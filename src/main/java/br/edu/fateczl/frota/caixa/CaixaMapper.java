package br.edu.fateczl.frota.caixa;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CaixaMapper {

    @Mapping(target = "id", ignore = true)
    Caixa toEntity(CaixaDTO dto);

    CaixaDTO toDto(Caixa entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(CaixaDTO dto, @MappingTarget Caixa entity);

}