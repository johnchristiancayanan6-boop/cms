package com.eastwest.mapper;

import com.eastwest.dto.WorkOrderBaseMiniDTO;
import com.eastwest.dto.workOrder.WorkOrderMiniDTO;
import com.eastwest.dto.workOrder.WorkOrderPatchDTO;
import com.eastwest.dto.workOrder.WorkOrderShowDTO;
import com.eastwest.dto.workOrder.WorkOrderPostDTO;
import com.eastwest.model.WorkOrder;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PartMapper.class, FileMapper.class, LocationMapper.class
        , TeamMapper.class, UserMapper.class,
        CustomerMapper.class, AssetMapper.class})
public interface WorkOrderMapper {
    WorkOrder updateWorkOrder(@MappingTarget WorkOrder entity, WorkOrderPatchDTO dto);

    @Mappings({})
    WorkOrderPatchDTO toPatchDto(WorkOrder model);

    @Mappings({
            @Mapping(source = "parentRequest.audioDescription", target = "audioDescription")
    })
    WorkOrderShowDTO toShowDto(WorkOrder model);

    WorkOrderMiniDTO toMiniDto(WorkOrder model);

    WorkOrderBaseMiniDTO toBaseMiniDto(WorkOrder model);

    WorkOrder fromPostDto(WorkOrderPostDTO workOrderPostDTO);

}
