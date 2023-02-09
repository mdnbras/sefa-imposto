package sefa.desafio.imposto.domain.parcelamento.pagamento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoDto {
    private Long idParcelamento;
    private int numeroParcela;
    private BigDecimal valorPago;
}
