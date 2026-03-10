package com.eastwest.mapper;

import com.eastwest.dto.CustomFieldPatchDTO;
import com.eastwest.model.CustomField;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CustomFieldMapper {
    CustomField updateCustomField(@MappingTarget CustomField entity, CustomFieldPatchDTO dto);

    @Mappings({})
    CustomFieldPatchDTO toPatchDto(CustomField model);
}
