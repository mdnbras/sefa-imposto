package sefa.desafio.imposto.domain.debito;

import org.springframework.stereotype.Service;
import sefa.desafio.imposto.config.Message;
import sefa.desafio.imposto.domain.pessoa.Pessoa;

import java.util.List;

@Service
public class DebitoService {

    private final DebitoRepository debitoRepository;

    public DebitoService(DebitoRepository debitoRepository) {
        this.debitoRepository = debitoRepository;
    }

    public List<Debito> getAllByPessoa(Pessoa pessoa) {
        return debitoRepository.getAllByPessoa(pessoa);
    }

    public Debito findById(Long idDebito) {
        return debitoRepository.findById(idDebito)
                .orElseThrow(() -> new RuntimeException(Message.EXCEPTION_DEBITO_NOT_FOUND));
    }

    public Long getId(Debito debito) {
        return debito.getId();
    }

}
