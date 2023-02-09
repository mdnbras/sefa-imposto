package sefa.desafio.imposto.domain.pessoa;

import lombok.Getter;
import lombok.Setter;
import sefa.desafio.imposto.base.BaseDto;

import javax.persistence.Column;

@Getter
@Setter
public class PessoaDto extends BaseDto {
    private String cpfCnpj;
    private String nome;
}
