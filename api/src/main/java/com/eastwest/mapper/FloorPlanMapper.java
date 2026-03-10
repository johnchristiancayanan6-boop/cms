package com.eastwest.mapper;

import com.eastwest.dto.FloorPlanPatchDTO;
import com.eastwest.dto.FloorPlanShowDTO;
import com.eastwest.model.FloorPlan;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface FloorPlanMapper {
    FloorPlan updateFloorPlan(@MappingTarget FloorPlan entity, FloorPlanPatchDTO dto);

    @Mappings({})
    FloorPlanPatchDTO toPatchDto(FloorPlan model);

    FloorPlanShowDTO toShowDto(FloorPlan model);
}
