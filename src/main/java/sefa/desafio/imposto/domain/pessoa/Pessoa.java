package sefa.desafio.imposto.domain.pessoa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sefa.desafio.imposto.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa extends BaseEntity {
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @Column(name = "nome")
    private String nome;
}
