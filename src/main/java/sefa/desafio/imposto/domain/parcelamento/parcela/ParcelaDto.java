package sefa.desafio.imposto.domain.parcelamento.parcela;

import lombok.Getter;
import lombok.Setter;
import sefa.desafio.imposto.base.BaseDto;

import java.math.BigDecimal;

@Getter
@Setter
public class ParcelaDto extends BaseDto {
    private BigDecimal valor;
    private int numero;
    private boolean pago;
}
