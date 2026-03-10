package com.eastwest.mapper;

import com.eastwest.dto.CategoryPatchDTO;
import com.eastwest.model.PartCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PartCategoryMapper {
    PartCategory updatePartCategory(@MappingTarget PartCategory entity, CategoryPatchDTO dto);

    @Mappings({})
    CategoryPatchDTO toPatchDto(PartCategory model);
}
