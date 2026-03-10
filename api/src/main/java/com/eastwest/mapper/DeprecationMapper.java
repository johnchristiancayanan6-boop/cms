package com.eastwest.mapper;

import com.eastwest.dto.DeprecationPatchDTO;
import com.eastwest.model.Deprecation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DeprecationMapper {
    Deprecation updateDeprecation(@MappingTarget Deprecation entity, DeprecationPatchDTO dto);

    @Mappings({})
    DeprecationPatchDTO toPatchDto(Deprecation model);
}
