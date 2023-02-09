package sefa.desafio.imposto.domain.parcelamento.parcela;

import lombok.*;
import sefa.desafio.imposto.base.BaseEntity;
import sefa.desafio.imposto.domain.parcelamento.Parcelamento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity(name = "parcela")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parcela extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_parcelamento")
    private Parcelamento parcelamento;

    @Column(name = "valor_parcela")
    private BigDecimal valor;

    @Column(name = "numero_parcela")
    private int numero;

    @Column(name = "pago")
    private boolean pago;

}
