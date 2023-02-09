package sefa.desafio.imposto.domain.parcelamento;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sefa.desafio.imposto.base.BaseDto;
import sefa.desafio.imposto.domain.parcelamento.parcela.ParcelaDto;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelamentoDto extends BaseDto {
    private Long idPessoa;
    private int qtdParcelasTotais;
    private int qtdParcelasPagas;
    private BigDecimal valorParcela;
    private TipoSituacao situacao;
    private List<Long> listDebitos;
    private List<ParcelaDto> parcelas;
}
