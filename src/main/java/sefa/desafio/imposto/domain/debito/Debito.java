package sefa.desafio.imposto.domain.debito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sefa.desafio.imposto.base.BaseEntity;
import sefa.desafio.imposto.domain.imposto.Imposto;
import sefa.desafio.imposto.domain.pessoa.Pessoa;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity(name = "debito")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Debito extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "id_imposto")
    private Imposto imposto;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    private BigDecimal valor;
}
