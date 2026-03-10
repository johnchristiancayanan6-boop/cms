package com.eastwest.mapper;

import com.eastwest.dto.CategoryPatchDTO;
import com.eastwest.model.MeterCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MeterCategoryMapper {
    MeterCategory updateMeterCategory(@MappingTarget MeterCategory entity, CategoryPatchDTO dto);

    @Mappings({})
    CategoryPatchDTO toPatchDto(MeterCategory model);
}
