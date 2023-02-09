package sefa.desafio.imposto.domain.parcelamento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import sefa.desafio.imposto.base.BaseEntity;
import sefa.desafio.imposto.domain.debito.Debito;
import sefa.desafio.imposto.domain.parcelamento.parcela.Parcela;
import sefa.desafio.imposto.domain.pessoa.Pessoa;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "parcelamento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parcelamento extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;

    @Column(name = "qtd_parcelas_totais")
    private int qtdParcelasTotais;

    @Column(name = "qtd_parcelas_pagas")
    private int qtdParcelasPagas;

    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;

    @Column(name = "valor_total_parcelado")
    private BigDecimal valorTotalParcelado;

    @Enumerated(EnumType.STRING)
    private TipoSituacao situacao;

    @CreationTimestamp
    @Column(name = "criado_em")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime criadoEm;

    @Transient
    private List<Parcela> parcelas;

    public Parcelamento(
            Pessoa pessoa,
            int qtdParcelasTotais,
            int qtdParcelasPagas,
            BigDecimal valorParcela,
            BigDecimal valorTotalParcelado,
            TipoSituacao situacao,
            LocalDateTime criadoEm
    ) {
        this.pessoa = pessoa;
        this.qtdParcelasTotais = qtdParcelasTotais;
        this.qtdParcelasPagas = qtdParcelasPagas;
        this.valorParcela = valorParcela;
        this.valorTotalParcelado = valorTotalParcelado;
        this.situacao = situacao;
        this.criadoEm = criadoEm;
    }
}
