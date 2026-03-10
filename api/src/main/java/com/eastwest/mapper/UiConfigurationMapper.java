package com.eastwest.mapper;

import com.eastwest.dto.UiConfigurationPatchDTO;
import com.eastwest.model.UiConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UiConfigurationMapper {
    UiConfiguration updateUiConfiguration(@MappingTarget UiConfiguration entity, UiConfigurationPatchDTO dto);
}
