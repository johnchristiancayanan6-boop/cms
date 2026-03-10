package com.eastwest.mapper;

import com.eastwest.dto.WorkOrderMeterTriggerPatchDTO;
import com.eastwest.dto.WorkOrderMeterTriggerShowDTO;
import com.eastwest.model.WorkOrderMeterTrigger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {PartMapper.class, UserMapper.class, FileMapper.class})
public interface WorkOrderMeterTriggerMapper {
    WorkOrderMeterTrigger updateWorkOrderMeterTrigger(@MappingTarget WorkOrderMeterTrigger entity, WorkOrderMeterTriggerPatchDTO dto);

    @Mappings({})
    WorkOrderMeterTriggerPatchDTO toPatchDto(WorkOrderMeterTrigger model);

    @Mappings({})
    WorkOrderMeterTriggerShowDTO toShowDto(WorkOrderMeterTrigger model);
}
