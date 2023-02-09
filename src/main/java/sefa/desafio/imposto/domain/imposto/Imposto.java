package sefa.desafio.imposto.domain.imposto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sefa.desafio.imposto.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "imposto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Imposto extends BaseEntity {

    @Column(name = "descricao")
    private String descricao;
}
