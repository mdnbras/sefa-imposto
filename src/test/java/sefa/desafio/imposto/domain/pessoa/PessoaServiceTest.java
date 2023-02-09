package sefa.desafio.imposto.domain.pessoa;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sefa.desafio.imposto.config.Message;
import sefa.desafio.imposto.domain.debito.Debito;
import sefa.desafio.imposto.domain.debito.DebitoService;
import sefa.desafio.imposto.domain.imposto.Imposto;
import sefa.desafio.imposto.domain.parcelamento.Parcelamento;
import sefa.desafio.imposto.domain.parcelamento.ParcelamentoService;
import sefa.desafio.imposto.domain.parcelamento.TipoSituacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private DebitoService debitoService;

    @Mock
    private ParcelamentoService parcelamentoService;

    @Test
    void deveriaListarDebitosDeUmaPessoaPorId_ComSucesso() {

        // Given
        final var idPessoaA = 1L;
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");
        final var impostoA = new Imposto("IPVA");
        final var impostoB = new Imposto("IMPOSTO");

        final var expectedDebitos = new ArrayList<Debito>();
        expectedDebitos.add(new Debito(impostoA, pessoaA, BigDecimal.valueOf(100.0)));
        expectedDebitos.add(new Debito(impostoB, pessoaA, BigDecimal.valueOf(200.0)));

        // When
        when(pessoaRepository.findById(idPessoaA))
                .thenReturn(Optional.of(pessoaA));

        when(debitoService.getAllByPessoa(pessoaA))
                .thenReturn(expectedDebitos);

        final var actualDebitos = pessoaService.getAllDebitosByIdPessoa(idPessoaA);

        // Then
        assertEquals(expectedDebitos, actualDebitos);
        assertEquals(expectedDebitos.size(), actualDebitos.size());
    }

    @Test
    void deveriaFalharAoListarDebitosDeUmaPessoaId_PessoaNaoExiste() {

        // Given
        final var idPessoaA = 1L;
        final var expectedErrorMessage = Message.EXCEPTION_PESSOA_NOT_FOUND;

        // When
        when(pessoaRepository.findById(idPessoaA))
                .thenReturn(Optional.empty());

        // Then
        final var actualException =
                assertThrows(RuntimeException.class, () -> pessoaService.getAllDebitosByIdPessoa(idPessoaA));

        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void deveriaListarParcelamentosDeUmaPessoaPorId_ComSucesso() {
        // Given
        final var idPessoaA = 1L;
        final var pessoaA = new Pessoa("51898687021", "Pessoa A");

        final var impostoA = new Imposto("IPVA");

        final var debitoA = new Debito(impostoA, pessoaA, BigDecimal.valueOf(500.0));

        final var expectedParcelamentos = new ArrayList<Parcelamento>();
        expectedParcelamentos.add(
                new Parcelamento(pessoaA,  2, 0, BigDecimal.valueOf(250.0), debitoA.getValor(), TipoSituacao.ATIVO, LocalDateTime.now())
        );

        // When
        when(pessoaRepository.findById(idPessoaA))
                .thenReturn(Optional.of(pessoaA));

        when(parcelamentoService.getAllByPessoa(pessoaA))
                .thenReturn(expectedParcelamentos);

        // Then
        final var actualParcelamentos = pessoaService.getAllParcelamentosByIdPessoa(idPessoaA);

        assertEquals(expectedParcelamentos, actualParcelamentos);
        assertEquals(expectedParcelamentos.size(), actualParcelamentos.size());
    }

    @Test
    void deveriaFalharAoListarParcelamentosDeUmaPessoaId_PessoaNaoExiste() {

        // Given
        final var idPessoaA = 1L;
        final var expectedErrorMessage = Message.EXCEPTION_PESSOA_NOT_FOUND;

        // When
        when(pessoaRepository.findById(idPessoaA))
                .thenReturn(Optional.empty());

        // Then
        final var actualException =
                assertThrows(RuntimeException.class, () -> pessoaService.getAllParcelamentosByIdPessoa(idPessoaA));

        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

}