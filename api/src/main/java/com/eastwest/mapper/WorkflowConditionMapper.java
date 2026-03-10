package com.eastwest.mapper;

import com.eastwest.dto.WorkflowConditionPatchDTO;
import com.eastwest.dto.WorkflowConditionPostDTO;
import com.eastwest.dto.WorkflowConditionShowDTO;
import com.eastwest.model.WorkflowCondition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {AssetMapper.class, LocationMapper.class, UserMapper.class,
        TeamMapper.class, VendorMapper.class, PartMapper.class, WorkOrderCategoryMapper.class,
        PurchaseOrderCategoryMapper.class})
public interface WorkflowConditionMapper {
    WorkflowCondition updateWorkflowCondition(@MappingTarget WorkflowCondition entity, WorkflowConditionPatchDTO dto);

    @Mappings({})
    WorkflowConditionPatchDTO toPatchDto(WorkflowCondition model);

    @Mappings({})
    WorkflowCondition toModel(WorkflowConditionPostDTO dto);

    WorkflowConditionShowDTO toShowDto(WorkflowCondition model);
}
