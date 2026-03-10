package com.eastwest.mapper;

import com.eastwest.dto.WorkflowActionPatchDTO;
import com.eastwest.dto.WorkflowActionPostDTO;
import com.eastwest.dto.WorkflowActionShowDTO;
import com.eastwest.model.WorkflowAction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {AssetMapper.class, LocationMapper.class, UserMapper.class,
        TeamMapper.class, WorkOrderCategoryMapper.class, ChecklistMapper.class, VendorMapper.class,
        PurchaseOrderCategoryMapper.class})
public interface WorkflowActionMapper {
    WorkflowAction updateWorkflowAction(@MappingTarget WorkflowAction entity, WorkflowActionPatchDTO dto);

    @Mappings({})
    WorkflowActionPatchDTO toPatchDto(WorkflowAction model);

    @Mappings({})
    WorkflowAction toModel(WorkflowActionPostDTO dto);

    WorkflowActionShowDTO toShowDto(WorkflowAction model);
}
