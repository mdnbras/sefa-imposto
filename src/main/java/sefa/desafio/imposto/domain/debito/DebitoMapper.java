package sefa.desafio.imposto.domain.debito;

import org.mapstruct.Mapper;
import sefa.desafio.imposto.base.BaseMapper;
import sefa.desafio.imposto.domain.imposto.ImpostoMapper;
import sefa.desafio.imposto.domain.pessoa.PessoaMapper;

@Mapper(uses = {
        ImpostoMapper.class,
        PessoaMapper.class
})
public abstract class DebitoMapper extends BaseMapper<Debito, DebitoDto> {

    @Override
    public abstract DebitoDto toDto(Debito entity);

    @Override
    public abstract Debito toEntity(DebitoDto dto);
}
