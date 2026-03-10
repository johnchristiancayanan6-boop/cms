package com.eastwest.mapper;

import com.eastwest.dto.LaborPatchDTO;
import com.eastwest.model.Labor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LaborMapper {
    Labor updateLabor(@MappingTarget Labor entity, LaborPatchDTO dto);

    @Mappings({})
    LaborPatchDTO toPatchDto(Labor model);
}
