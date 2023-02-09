package sefa.desafio.imposto.domain.pessoa;

import org.springframework.stereotype.Service;
import sefa.desafio.imposto.base.BaseException;
import sefa.desafio.imposto.config.Message;
import sefa.desafio.imposto.domain.debito.Debito;
import sefa.desafio.imposto.domain.debito.DebitoService;
import sefa.desafio.imposto.domain.parcelamento.Parcelamento;
import sefa.desafio.imposto.domain.parcelamento.ParcelamentoService;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository repository;
    private final DebitoService debitoService;
    private final ParcelamentoService parcelamentoService;

    public PessoaService(
            PessoaRepository repository,
            DebitoService debitoService,
            ParcelamentoService parcelamentoService
    ) {
        this.repository = repository;
        this.debitoService = debitoService;
        this.parcelamentoService = parcelamentoService;
    }

    public List<Debito> getAllDebitosByIdPessoa(final Long idPessoa) {
        final var pessoa = repository.findById(idPessoa);
        return pessoa
                .map(debitoService::getAllByPessoa)
                .orElseThrow(() -> new BaseException(Message.EXCEPTION_PESSOA_NOT_FOUND));
    }

    public List<Parcelamento> getAllParcelamentosByIdPessoa(final Long idPessoa) {
        final var pessoa = repository.findById(idPessoa);
        return pessoa
                .map(parcelamentoService::getAllByPessoa)
                .orElseThrow(() -> new BaseException(Message.EXCEPTION_PESSOA_NOT_FOUND));
    }

}
