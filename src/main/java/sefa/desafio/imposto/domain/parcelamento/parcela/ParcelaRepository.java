package sefa.desafio.imposto.domain.parcelamento.parcela;

import org.springframework.stereotype.Repository;
import sefa.desafio.imposto.base.BaseRepository;
import sefa.desafio.imposto.domain.parcelamento.Parcelamento;

import java.util.List;

@Repository
public interface ParcelaRepository extends BaseRepository<Parcela> {
    Parcela getByParcelamentoAndNumero(Parcelamento parcelamento, int numeroParcela);
    List<Parcela> getAllByParcelamento(Parcelamento parcelamento);
}
