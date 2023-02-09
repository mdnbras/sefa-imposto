package sefa.desafio.imposto.domain.debito;

import lombok.Getter;
import lombok.Setter;
import sefa.desafio.imposto.base.BaseDto;
import sefa.desafio.imposto.domain.imposto.ImpostoDto;
import sefa.desafio.imposto.domain.pessoa.PessoaDto;

import java.math.BigDecimal;

@Getter
@Setter
public class DebitoDto extends BaseDto {
    private ImpostoDto imposto;
    private PessoaDto pessoa;
    private BigDecimal valor;
}
