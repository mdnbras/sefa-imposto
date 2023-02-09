package sefa.desafio.imposto.base;

import org.mapstruct.Mapping;

public abstract class BaseMapper<Entity extends BaseEntity, Dto extends BaseDto> {

    public abstract Dto toDto(Entity entity);

    @Mapping(target = "id", ignore = true)
    public abstract Entity toEntity(Dto dto);
}
