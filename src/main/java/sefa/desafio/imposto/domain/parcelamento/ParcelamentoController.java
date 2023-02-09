package sefa.desafio.imposto.domain.parcelamento;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sefa.desafio.imposto.domain.parcelamento.pagamento.PagamentoDto;

@RestController
@RequestMapping("/v1/parcelamento")
public class ParcelamentoController {

    private final ParcelamentoService parcelamentoService;

    public ParcelamentoController(ParcelamentoService parcelamentoService) {
        this.parcelamentoService = parcelamentoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarParcelamento(@RequestBody ParcelamentoDto parcelamentoDto) {
        parcelamentoService.criarParcelamento(parcelamentoDto);
    }

    @PostMapping("/cancelar/{id}")
    public void cancelarParcelamento(@PathVariable("id") final Long idParcelamento) {
        parcelamentoService.cancelarParcelamento(idParcelamento);
    }

    @PostMapping("/efetuar-pagamento")
    public void efetuarPagamentoParcela(@RequestBody PagamentoDto pagamentoDto) {
        parcelamentoService.efetuarPagamento(pagamentoDto);
    }
}
