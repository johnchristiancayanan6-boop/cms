package com.eastwest.mapper;

import com.eastwest.dto.AdditionalCostPatchDTO;
import com.eastwest.model.AdditionalCost;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AdditionalCostMapper {
    AdditionalCost updateAdditionalCost(@MappingTarget AdditionalCost entity, AdditionalCostPatchDTO dto);

    @Mappings({})
    AdditionalCostPatchDTO toPatchDto(AdditionalCost model);
}
