package com.eastwest.mapper;

import com.eastwest.dto.WorkflowPatchDTO;
import com.eastwest.dto.WorkflowShowDTO;
import com.eastwest.model.Workflow;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {WorkflowConditionMapper.class, WorkflowActionMapper.class})
public interface WorkflowMapper {
    Workflow updateWorkflow(@MappingTarget Workflow entity, WorkflowPatchDTO dto);

    @Mappings({})
    WorkflowPatchDTO toPatchDto(Workflow model);

    WorkflowShowDTO toShowDto(Workflow model);
}
