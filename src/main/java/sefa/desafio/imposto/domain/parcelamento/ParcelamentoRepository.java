package sefa.desafio.imposto.domain.parcelamento;

import org.springframework.stereotype.Repository;
import sefa.desafio.imposto.base.BaseRepository;
import sefa.desafio.imposto.domain.pessoa.Pessoa;

import java.util.List;

@Repository
interface ParcelamentoRepository extends BaseRepository<Parcelamento> {
    List<Parcelamento> getAllByPessoa(Pessoa pessoa);
}
