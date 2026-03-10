package com.eastwest.mapper;

import com.eastwest.dto.CategoryPatchDTO;
import com.eastwest.model.AssetCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AssetCategoryMapper {
    AssetCategory updateAssetCategory(@MappingTarget AssetCategory entity, CategoryPatchDTO dto);

    @Mappings({})
    CategoryPatchDTO toPatchDto(AssetCategory model);
}
