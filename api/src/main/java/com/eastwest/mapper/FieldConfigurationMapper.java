package com.eastwest.mapper;

import com.eastwest.dto.FieldConfigurationPatchDTO;
import com.eastwest.model.FieldConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FieldConfigurationMapper {
    FieldConfiguration updateFieldConfiguration(@MappingTarget FieldConfiguration entity, FieldConfigurationPatchDTO dto);

    @Mappings({})
    FieldConfigurationPatchDTO toPatchDto(FieldConfiguration model);
}
