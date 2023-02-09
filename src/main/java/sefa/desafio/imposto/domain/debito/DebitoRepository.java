package sefa.desafio.imposto.domain.debito;

import org.springframework.stereotype.Repository;
import sefa.desafio.imposto.base.BaseRepository;
import sefa.desafio.imposto.domain.pessoa.Pessoa;

import java.util.List;

@Repository
interface DebitoRepository extends BaseRepository<Debito> {
    List<Debito> getAllByPessoa(Pessoa pessoa);
}
