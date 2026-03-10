package com.eastwest.mapper;

import com.eastwest.dto.PartQuantityPatchDTO;
import com.eastwest.dto.PartQuantityShowDTO;
import com.eastwest.model.PartQuantity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {PartMapper.class, PurchaseOrderMapper.class, WorkOrderMapper.class})
public interface PartQuantityMapper {
    PartQuantity updatePartQuantity(@MappingTarget PartQuantity entity, PartQuantityPatchDTO dto);

    @Mappings({})
    PartQuantityPatchDTO toPatchDto(PartQuantity model);

    PartQuantityShowDTO toShowDto(PartQuantity model);

}
