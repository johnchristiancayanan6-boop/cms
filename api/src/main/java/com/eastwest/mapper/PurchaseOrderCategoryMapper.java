package com.eastwest.mapper;

import com.eastwest.dto.CategoryMiniDTO;
import com.eastwest.dto.CategoryPatchDTO;
import com.eastwest.model.PurchaseOrderCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PurchaseOrderCategoryMapper {
    PurchaseOrderCategory updatePurchaseOrderCategory(@MappingTarget PurchaseOrderCategory entity,
                                                      CategoryPatchDTO dto);

    @Mappings({})
    CategoryPatchDTO toPatchDto(PurchaseOrderCategory model);

    CategoryMiniDTO toMiniDto(PurchaseOrderCategory model);
}
