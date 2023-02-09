package sefa.desafio.imposto.domain.parcelamento;

import org.springframework.stereotype.Service;
import sefa.desafio.imposto.base.BaseException;
import sefa.desafio.imposto.config.Message;
import sefa.desafio.imposto.domain.debito.Debito;
import sefa.desafio.imposto.domain.debito.DebitoService;
import sefa.desafio.imposto.domain.parcelamento.pagamento.PagamentoDto;
import sefa.desafio.imposto.domain.parcelamento.parcela.Parcela;
import sefa.desafio.imposto.domain.parcelamento.parcela.ParcelaRepository;
import sefa.desafio.imposto.domain.parcelamentodebito.ParcelamentoDebito;
import sefa.desafio.imposto.domain.parcelamentodebito.ParcelamentoDebitoRepository;
import sefa.desafio.imposto.domain.pessoa.Pessoa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelamentoService {

    private final ParcelamentoRepository parcelamentoRepository;
    private final ParcelaRepository parcelaRepository;
    private final DebitoService debitoService;
    private final ParcelamentoDebitoRepository parcelamentoDebitoRepository;

    public ParcelamentoService(
            ParcelamentoRepository parcelamentoRepository,
            ParcelaRepository parcelaRepository,
            DebitoService debitoService,
            ParcelamentoDebitoRepository parcelamentoDebitoRepository) {
        this.parcelamentoRepository = parcelamentoRepository;
        this.parcelaRepository = parcelaRepository;
        this.debitoService = debitoService;
        this.parcelamentoDebitoRepository = parcelamentoDebitoRepository;
    }

    // region public methods
    public List<Parcelamento> getAllByPessoa(Pessoa pessoa) {
        final var parcelamentos = parcelamentoRepository.getAllByPessoa(pessoa);
        for (Parcelamento parcelamento : parcelamentos) {
            parcelamento.setParcelas(parcelaRepository.getAllByParcelamento(parcelamento));
        }
        return parcelamentos;
    }

    public Parcelamento criarParcelamento(ParcelamentoDto parcelamentoDto) {

        validarQuantidadeParcelas(parcelamentoDto);

        final var pessoa = new Pessoa();
        pessoa.setId(parcelamentoDto.getIdPessoa());

        final var listDebitos = new ArrayList<Debito>();
        var totalEmDebitos = BigDecimal.ZERO;
        for (Long idDebito : parcelamentoDto.getListDebitos()) {
            final var debito = debitoService.findById(idDebito);
            listDebitos.add(debito);
            totalEmDebitos = totalEmDebitos.add(debito.getValor());
        }

        validarDebitosComImpostoIgual(listDebitos);

        final var valorParcela = calcularValorParcela(totalEmDebitos, parcelamentoDto.getQtdParcelasTotais());

        validarValorMinimoParcela(valorParcela);

        final var parcelamento = new Parcelamento(
                pessoa,
                parcelamentoDto.getQtdParcelasTotais(),
                parcelamentoDto.getQtdParcelasPagas(),
                valorParcela,
                totalEmDebitos,
                TipoSituacao.ATIVO,
                LocalDateTime.now()
        );

        final var novoParcelamento = parcelamentoRepository.save(parcelamento);
        salvarParcelamentoDebitos(novoParcelamento, listDebitos);
        salvarParcelas(novoParcelamento);

        return novoParcelamento;
    }

    public void cancelarParcelamento(final Long idParcelamento) {
        final var parcelamento = findById(idParcelamento);

        parcelamento.setSituacao(TipoSituacao.CANCELADO);

        parcelamentoRepository.save(parcelamento);
    }

    public void efetuarPagamento(PagamentoDto pagamentoDto) {
        final var parcelamento = findById(pagamentoDto.getIdParcelamento());
        parcelamento.setQtdParcelasPagas(parcelamento.getQtdParcelasPagas() + 1);
        parcelamentoRepository.save(parcelamento);

        final var parcela = parcelaRepository.getByParcelamentoAndNumero(
                parcelamento,
                pagamentoDto.getNumeroParcela()
        );
        parcela.setPago(true);
        parcelaRepository.save(parcela);
    }
    // endregion

    // region private methods
    private void validarQuantidadeParcelas(ParcelamentoDto parcelamentoDto) {
        if (parcelamentoDto.getQtdParcelasTotais() > 12) {
            throw new BaseException(Message.EXCEPTION_PARCELAMENTO_QTD_PARCELAS_LIMIT);
        }
    }

    private void validarValorMinimoParcela(BigDecimal valorParcela) {
        if (valorParcela.doubleValue() < 100.0) {
            throw new BaseException(Message.EXCEPTION_PARCELAMENTO_VALOR_PARCELA_MIN);
        }
    }

    private void salvarParcelas(Parcelamento parcelamento) {
        final var parcelas = new ArrayList<Parcela>();
        for (int i = 1; i <= parcelamento.getQtdParcelasTotais(); i++) {
            parcelas.add(new Parcela(parcelamento, parcelamento.getValorParcela(), i, false));
        }
        parcelaRepository.saveAll(parcelas);
    }

    private void salvarParcelamentoDebitos(Parcelamento parcelamento, List<Debito> debitos) {
        final var parcelamentoDebitos = new ArrayList<ParcelamentoDebito>();
        for (Debito debito : debitos) {
            parcelamentoDebitos.add(new ParcelamentoDebito(parcelamento.getId(), debito.getId()));
        }
        parcelamentoDebitoRepository.saveAll(parcelamentoDebitos);
    }

    private BigDecimal calcularValorParcela(BigDecimal totalEmDebitos, int qtdParcelasTotais) {
        return totalEmDebitos
                .divide(BigDecimal.valueOf(qtdParcelasTotais), 2, RoundingMode.HALF_EVEN);
    }

    private void validarDebitosComImpostoIgual(ArrayList<Debito> debitos) {
        var temp = "";
        for (Debito debito : debitos) {
            if (temp.equals("")) {
                temp = debito.getImposto().getDescricao();
            } else if (!temp.equals(debito.getImposto().getDescricao())) {
                throw new BaseException(Message.EXCEPTION_PARCELAMENTO_IMPOSTO_DIFERENTE);
            }
        }
    }

    private Parcelamento findById(Long idParcelamento) {
        return parcelamentoRepository.findById(idParcelamento)
                .orElseThrow(() -> new BaseException(Message.EXCEPTION_PARCELAMENTO_NOT_FOUND));
    }
    // endregion

}
