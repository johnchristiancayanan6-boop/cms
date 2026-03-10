package com.eastwest.mapper;

import com.eastwest.dto.CategoryMiniDTO;
import com.eastwest.dto.CategoryPatchDTO;
import com.eastwest.model.WorkOrderCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface WorkOrderCategoryMapper {
    WorkOrderCategory updateWorkOrderCategory(@MappingTarget WorkOrderCategory entity, CategoryPatchDTO dto);

    @Mappings({})
    CategoryPatchDTO toPatchDto(WorkOrderCategory model);

    CategoryMiniDTO toMiniDto(WorkOrderCategory model);
}
