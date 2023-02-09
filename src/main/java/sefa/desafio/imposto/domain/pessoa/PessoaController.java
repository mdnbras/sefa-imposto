package sefa.desafio.imposto.domain.pessoa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sefa.desafio.imposto.domain.debito.DebitoDto;
import sefa.desafio.imposto.domain.debito.DebitoMapper;
import sefa.desafio.imposto.domain.parcelamento.ParcelamentoDto;
import sefa.desafio.imposto.domain.parcelamento.ParcelamentoMapper;

import java.util.List;

@RestController
@RequestMapping("/v1/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;
    private final DebitoMapper debitoMapper;
    private final ParcelamentoMapper parcelamentoMapper;

    public PessoaController(
            PessoaService pessoaService,
            DebitoMapper debitoMapper,
            ParcelamentoMapper parcelamentoMapper) {
        this.pessoaService = pessoaService;
        this.debitoMapper = debitoMapper;
        this.parcelamentoMapper = parcelamentoMapper;
    }

    @GetMapping("/{idPessoa}/meus-debitos")
    public ResponseEntity<List<DebitoDto>> getAllDebitosByIdPessoa(@PathVariable("idPessoa") final Long idPessoa) {
        return ResponseEntity.ok(
                pessoaService.getAllDebitosByIdPessoa(idPessoa)
                        .stream()
                        .map(debitoMapper::toDto)
                        .toList()
        );
    }

    @GetMapping("/{idPessoa}/meus-parcelamentos")
    public ResponseEntity<List<ParcelamentoDto>> getAllParcelamentosByIdPessoa(@PathVariable("idPessoa") final Long idPessoa) {
        return ResponseEntity.ok(
                pessoaService.getAllParcelamentosByIdPessoa(idPessoa)
                        .stream()
                        .map(parcelamentoMapper::toDto)
                        .toList()
        );
    }
}
