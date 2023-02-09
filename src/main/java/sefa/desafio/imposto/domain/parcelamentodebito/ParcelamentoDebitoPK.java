package sefa.desafio.imposto.domain.parcelamentodebito;

import java.io.Serializable;
import java.util.Objects;

public class ParcelamentoDebitoPK implements Serializable {
    private Long idParcelamento;
    private Long idDebito;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParcelamentoDebitoPK that = (ParcelamentoDebitoPK) o;
        return Objects.equals(idParcelamento, that.idParcelamento) && Objects.equals(idDebito, that.idDebito);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParcelamento, idDebito);
    }
}
