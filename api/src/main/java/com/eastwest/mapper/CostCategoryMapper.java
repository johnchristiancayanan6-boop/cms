package com.eastwest.mapper;

import com.eastwest.dto.CategoryPatchDTO;
import com.eastwest.dto.CategoryPostDTO;
import com.eastwest.model.CostCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CostCategoryMapper {
    CostCategory updateCostCategory(@MappingTarget CostCategory entity, CategoryPatchDTO dto);

    @Mappings({})
    CategoryPatchDTO toPatchDto(CostCategory model);

    @Mappings({})
    CostCategory toModel(CategoryPostDTO model);
}
