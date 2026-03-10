package com.eastwest.mapper;

import com.eastwest.dto.PurchaseOrderPatchDTO;
import com.eastwest.dto.PurchaseOrderShowDTO;
import com.eastwest.model.PurchaseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PurchaseOrderMapper {
    PurchaseOrder updatePurchaseOrder(@MappingTarget PurchaseOrder entity, PurchaseOrderPatchDTO dto);

    @Mappings({})
    PurchaseOrderPatchDTO toPatchDto(PurchaseOrder model);

    PurchaseOrderShowDTO toShowDto(PurchaseOrder model);
}
