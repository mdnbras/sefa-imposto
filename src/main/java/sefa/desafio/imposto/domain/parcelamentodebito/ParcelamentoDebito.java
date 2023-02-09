package sefa.desafio.imposto.domain.parcelamentodebito;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sefa.desafio.imposto.base.BaseEntity;
import sefa.desafio.imposto.domain.debito.Debito;
import sefa.desafio.imposto.domain.parcelamento.Parcelamento;

import javax.persistence.*;

@Entity(name = "parcelamento_has_debitos")
@NoArgsConstructor
@Getter
@Setter
@IdClass(ParcelamentoDebitoPK.class)
public class ParcelamentoDebito {

    @Id
    @Column(name = "id_parcelamento")
    private Long idParcelamento;

    @Id
    @Column(name = "id_debito")
    private Long idDebito;

    public ParcelamentoDebito(Long idParcelamento, Long idDebito) {
        this.idParcelamento = idParcelamento;
        this.idDebito = idDebito;
    }

}
