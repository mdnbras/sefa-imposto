package sefa.desafio.imposto.domain.parcelamento;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sefa.desafio.imposto.config.Message;
import sefa.desafio.imposto.domain.debito.Debito;
import sefa.desafio.imposto.domain.debito.DebitoService;
import sefa.desafio.imposto.domain.imposto.Imposto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParcelamentoServiceTest {

    @InjectMocks
    private ParcelamentoService parcelamentoService;

    @Mock
    private ParcelaRepository parcelaRepository;

    @Mock
    private ParcelamentoRepository parcelamentoRepository;

    @Mock
    private DebitoService debitoService;

    @Mock
    private ParcelamentoDebitoRepository parcelamentoDebitoRepository;

    @Captor
    private ArgumentCaptor<ArrayList<Parcela>> parcelasCriadasCaptor;

    @Captor
    private ArgumentCaptor<Parcelamento> parcelamentoCanceladoCaptor;

    @Captor
    private ArgumentCaptor<Parcela> parcelaPagaCaptor;

    @Captor
    private ArgumentCaptor<Parcelamento> parcelamentoCaptor;

    @Test
    void deveriaTestarMetodo_getAllByPessoa_ComSucesso() {
        // Given
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");
        pessoaA.setId(1L);
        final var impostoA = new Imposto("IPVA");
        impostoA.setId(1L);
        final var debitoA = new Debito(impostoA, pessoaA, BigDecimal.valueOf(500.0));
        debitoA.setId(1L);
        final var qtdParcelastotais = 2;
        final var qtdParcelasPagas = 0;
        final var valorParcela = debitoA.getValor()
                .divide(BigDecimal.valueOf(qtdParcelastotais), 2, RoundingMode.HALF_UP);
        final var expectedParcelamento = new Parcelamento(
                pessoaA,
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                debitoA.getValor(),
                TipoSituacao.ATIVO,
                LocalDateTime.now()
        );
        expectedParcelamento.setId(1L);

        // When
        when(parcelamentoRepository.getAllByPessoa(any()))
                .thenReturn(List.of(expectedParcelamento));

        // Then
        final var actualParcelamentos = parcelamentoService.getAllByPessoa(pessoaA);

        assertEquals(List.of(expectedParcelamento), actualParcelamentos);
    }

    @Test
    void deveriaCriarUmParcelamentoParaUmaPessoa_ComSucesso() {
        // Given
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");
        pessoaA.setId(1L);

        final var impostoA = new Imposto("IPVA");
        impostoA.setId(1L);

        final var impostoB = new Imposto("IPVA");
        impostoB.setId(2L);

        final var debitoA = new Debito(impostoA, pessoaA, BigDecimal.valueOf(500.0));
        debitoA.setId(1L);

        final var debitoB = new Debito(impostoB, pessoaA, BigDecimal.valueOf(400.0));
        debitoB.setId(2L);

        final var listIdsDebitos = new ArrayList<>(List.of(debitoA.getId(), debitoB.getId()));

        final var qtdParcelastotais = 2;
        final var qtdParcelasPagas = 0;
        final var totalEmDebitos = debitoA.getValor()
                .add(debitoB.getValor());
        final var valorParcela = totalEmDebitos
                .divide(BigDecimal.valueOf(qtdParcelastotais), 2, RoundingMode.HALF_UP);

        final var parcelamentoDto = new ParcelamentoDto(
                pessoaA.getId(),
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                TipoSituacao.ATIVO,
                listIdsDebitos,
                null
        );
        final var expectedParcelamento = new Parcelamento(
                pessoaA,
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                totalEmDebitos,
                TipoSituacao.ATIVO,
                LocalDateTime.now()
        );
        expectedParcelamento.setId(1L);

        final var parcelaA = new Parcela(expectedParcelamento, valorParcela, 1, false);
        parcelaA.setId(1L);
        final var parcelaB = new Parcela(expectedParcelamento, valorParcela, 2, false);
        parcelaB.setId(2L);
        final var expectedParcelas = new ArrayList<>(List.of(parcelaA, parcelaB));

        final var parcelamentoDebitoA = new ParcelamentoDebito(expectedParcelamento.getId(), debitoA.getId());
        final var parcelamentoDebitoB = new ParcelamentoDebito(expectedParcelamento.getId(), debitoB.getId());

        final var expectedParcelamentoDebitos = new ArrayList<>(List.of(parcelamentoDebitoA, parcelamentoDebitoB));

        // When
        when(debitoService.findById(debitoA.getId()))
                .thenReturn(debitoA);

        when(debitoService.findById(debitoB.getId()))
                .thenReturn(debitoB);

        when(parcelamentoRepository.save(any()))
                .thenReturn(expectedParcelamento);

        when(parcelaRepository.saveAll(any()))
                .thenReturn(expectedParcelas);

        when(parcelamentoDebitoRepository.saveAll(any()))
                .thenReturn(expectedParcelamentoDebitos);

        // Then
        final var actualParcelamento = parcelamentoService
                .criarParcelamento(parcelamentoDto);

        verify(parcelaRepository, times(1))
                .saveAll(parcelasCriadasCaptor.capture());

        assertEquals(expectedParcelamento.getId(), actualParcelamento.getId());
        assertEquals(expectedParcelamento.getSituacao(), actualParcelamento.getSituacao());
        assertEquals(expectedParcelamento.getValorParcela(), actualParcelamento.getValorParcela());
        assertEquals(expectedParcelamento.getQtdParcelasPagas(), actualParcelamento.getQtdParcelasPagas());
        assertEquals(expectedParcelamento.getQtdParcelasTotais(), actualParcelamento.getQtdParcelasTotais());
        assertEquals(expectedParcelamento.getPessoa(), actualParcelamento.getPessoa());

        assertEquals(expectedParcelas.size(), parcelasCriadasCaptor.getValue().size());
        assertEquals(expectedParcelas.get(0).getParcelamento().getId(), parcelasCriadasCaptor.getValue().get(0).getParcelamento().getId());
        assertEquals(expectedParcelas.get(0).getValor(), parcelasCriadasCaptor.getValue().get(0).getValor());
    }

    @Test
    void deveriaFalharAoCriarUmParcelamento_LimiteParcelasAtingido() {
        // Given
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");
        pessoaA.setId(1L);

        final var impostoA = new Imposto("IPVA");
        impostoA.setId(1L);

        final var debitoA = new Debito(impostoA, pessoaA, BigDecimal.valueOf(500.0));
        debitoA.setId(1L);
        final var listIdsDebitos = new ArrayList<>(List.of(debitoA.getId()));

        final var qtdParcelastotais = 20;
        final var qtdParcelasPagas = 0;
        final var valorParcela = debitoA.getValor()
                .divide(BigDecimal.valueOf(qtdParcelastotais), 2, RoundingMode.HALF_UP);

        final var parcelamentoDto = new ParcelamentoDto(
                pessoaA.getId(),
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                TipoSituacao.ATIVO,
                listIdsDebitos,
                null
        );

        final var expectedErrorMessage = Message.EXCEPTION_PARCELAMENTO_QTD_PARCELAS_LIMIT;

        // When - Nothing


        // Then
        final var actualException = assertThrows(
                RuntimeException.class,
                () -> parcelamentoService.criarParcelamento(parcelamentoDto)
        );

        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void deveriaFalharAoCriarUmParcelamento_LimiteMinimoValorParcelaAtingido() {
        // Given
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");
        pessoaA.setId(1L);

        final var impostoA = new Imposto("IPVA");
        impostoA.setId(1L);

        final var debitoA = new Debito(impostoA, pessoaA, BigDecimal.valueOf(1000.0));
        debitoA.setId(1L);
        final var listIdsDebitos = new ArrayList<>(List.of(debitoA.getId()));

        final var qtdParcelastotais = 12;
        final var qtdParcelasPagas = 0;
        final var valorParcela = debitoA.getValor()
                .divide(BigDecimal.valueOf(qtdParcelastotais), 2, RoundingMode.HALF_EVEN);

        final var parcelamentoDto = new ParcelamentoDto(
                pessoaA.getId(),
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                TipoSituacao.ATIVO,
                listIdsDebitos,
                null
        );

        final var expectedErrorMessage = Message.EXCEPTION_PARCELAMENTO_VALOR_PARCELA_MIN;

        // When
        when(debitoService.findById(debitoA.getId()))
                .thenReturn(debitoA);

        // Then
        final var actualException = assertThrows(
                RuntimeException.class,
                () -> parcelamentoService.criarParcelamento(parcelamentoDto)
        );

        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void deveriaFalharAoCriarUmParcelamento_ImpostoDiferenteParaUmParcelamento() {
        // Given
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");
        pessoaA.setId(1L);

        final var impostoA = new Imposto("IPVA");
        impostoA.setId(1L);

        final var impostoB = new Imposto("ICMS");
        impostoB.setId(2L);

        final var debitoA = new Debito(impostoA, pessoaA, BigDecimal.valueOf(1000.0));
        debitoA.setId(1L);

        final var debitoB = new Debito(impostoB, pessoaA, BigDecimal.valueOf(800.0));
        debitoB.setId(2L);

        final var listIdsDebitos = new ArrayList<>(List.of(debitoA.getId(), debitoB.getId()));

        final var qtdParcelastotais = 12;
        final var qtdParcelasPagas = 0;
        final var valorParcela = debitoA.getValor()
                .add(debitoB.getValor())
                .divide(BigDecimal.valueOf(qtdParcelastotais), 2, RoundingMode.HALF_EVEN);

        final var parcelamentoDto = new ParcelamentoDto(
                pessoaA.getId(),
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                TipoSituacao.ATIVO,
                listIdsDebitos,
                null
        );

        final var expectedErrorMessage = Message.EXCEPTION_PARCELAMENTO_IMPOSTO_DIFERENTE;

        // When
        when(debitoService.findById(debitoA.getId()))
                .thenReturn(debitoA);

        when(debitoService.findById(debitoB.getId()))
                .thenReturn(debitoB);

        // Then
        final var actualException = assertThrows(
                RuntimeException.class,
                () -> parcelamentoService.criarParcelamento(parcelamentoDto)
        );

        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void deveriaCancelarUmParcelamento_ComSucesso() {
        // Given
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");
        pessoaA.setId(1L);

        final var impostoA = new Imposto("IPVA");
        impostoA.setId(1L);

        final var impostoB = new Imposto("ICMS");
        impostoB.setId(2L);

        final var debitoA = new Debito(impostoA, pessoaA, BigDecimal.valueOf(1000.0));
        debitoA.setId(1L);

        final var debitoB = new Debito(impostoB, pessoaA, BigDecimal.valueOf(800.0));
        debitoB.setId(2L);

        final var qtdParcelastotais = 12;
        final var qtdParcelasPagas = 0;
        final var totalEmDebitos = debitoA.getValor()
                .add(debitoB.getValor());
        final var valorParcela = totalEmDebitos
                .divide(BigDecimal.valueOf(qtdParcelastotais), 2, RoundingMode.HALF_EVEN);

        final var expectedParcelamentoAtivo = new Parcelamento(
                pessoaA,
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                totalEmDebitos,
                TipoSituacao.ATIVO,
                LocalDateTime.now()
        );
        expectedParcelamentoAtivo.setId(1L);

        final var expectedParcelamentoCancelado = new Parcelamento(
                pessoaA,
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                totalEmDebitos,
                TipoSituacao.CANCELADO,
                LocalDateTime.now()
        );
        expectedParcelamentoCancelado.setId(1L);

        // When
        when(parcelamentoRepository.findById(expectedParcelamentoAtivo.getId()))
                .thenReturn(Optional.of(expectedParcelamentoAtivo));

        when(parcelamentoRepository.save(any()))
                .thenReturn(expectedParcelamentoCancelado);

        parcelamentoService.cancelarParcelamento(expectedParcelamentoAtivo.getId());

        // Then
        verify(parcelamentoRepository, times(1))
                .save(parcelamentoCanceladoCaptor.capture());

        assertEquals(expectedParcelamentoCancelado.getSituacao(), parcelamentoCanceladoCaptor.getValue().getSituacao());
    }

    @Test
    void deveriaReceberUmPagamentoDeParcelamento_ComSucesso() {
        // Given
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");
        pessoaA.setId(1L);

        final var impostoA = new Imposto("IPVA");
        impostoA.setId(1L);

        final var debitoA = new Debito(impostoA, pessoaA, BigDecimal.valueOf(1200.0));
        debitoA.setId(1L);

        final var qtdParcelastotais = 12;
        final var qtdParcelasPagas = 0;

        final var totalEmDebitos = debitoA.getValor();

        final var valorParcela = totalEmDebitos
                .divide(BigDecimal.valueOf(qtdParcelastotais), 2, RoundingMode.HALF_EVEN);

        final var expectedParcelamentoAtivo = new Parcelamento(
                pessoaA,
                qtdParcelastotais,
                qtdParcelasPagas,
                valorParcela,
                totalEmDebitos,
                TipoSituacao.ATIVO,
                LocalDateTime.now()
        );
        expectedParcelamentoAtivo.setId(1L);

        final var numeroParcelaAPagar = 1;
        final var valorParcelaAPagar = BigDecimal.valueOf(120.0);

        final var expectedPagamentoDto = new PagamentoDto(
                expectedParcelamentoAtivo.getId(),
                numeroParcelaAPagar,
                valorParcelaAPagar
        );

        final var expectedParcela = new Parcela(
                expectedParcelamentoAtivo,
                BigDecimal.valueOf(100.0),
                1,
                false
        );

        final var expectedParcelaPaga = new Parcela(
                expectedParcelamentoAtivo,
                BigDecimal.valueOf(100.0),
                1,
                true
        );

        // When
        when(parcelamentoRepository.findById(expectedParcelamentoAtivo.getId()))
                .thenReturn(Optional.of(expectedParcelamentoAtivo));

        when(parcelaRepository.getByParcelamentoAndNumero(expectedParcelamentoAtivo, numeroParcelaAPagar))
                .thenReturn(expectedParcela);

        when(parcelaRepository.save(any()))
                .thenReturn(expectedParcelaPaga);

        parcelamentoService.efetuarPagamento(expectedPagamentoDto);

        //Then
        verify(parcelaRepository, times(1))
                .save(parcelaPagaCaptor.capture());

        verify(parcelamentoRepository, times(1))
                .save(parcelamentoCaptor.capture());

        assertEquals(expectedParcelaPaga.getId(), parcelaPagaCaptor.getValue().getId());
        assertEquals(expectedParcelaPaga.isPago(), parcelaPagaCaptor.getValue().isPago());

        assertEquals(1, parcelamentoCaptor.getValue().getQtdParcelasPagas());
    }


}