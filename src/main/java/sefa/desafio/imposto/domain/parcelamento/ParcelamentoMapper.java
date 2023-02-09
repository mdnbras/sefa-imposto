package sefa.desafio.imposto.domain.parcelamento;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import sefa.desafio.imposto.base.BaseMapper;
import sefa.desafio.imposto.domain.parcelamento.parcela.ParcelaMapper;

@Mapper(uses = {
        ParcelaMapper.class
})
public abstract class ParcelamentoMapper extends BaseMapper<Parcelamento, ParcelamentoDto> {

    @Mappings(value = {
            @Mapping(source = "pessoa.id", target = "idPessoa"),
    })
    @Override
    public abstract ParcelamentoDto toDto(Parcelamento entity);

    @Mappings(value = {
            @Mapping(source = "idPessoa", target = "pessoa.id"),
            @Mapping(target = "criadoEm", ignore = true)
    })
    @Override
    public abstract Parcelamento toEntity(ParcelamentoDto dto);
}
