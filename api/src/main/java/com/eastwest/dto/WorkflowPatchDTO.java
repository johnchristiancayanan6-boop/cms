package com.eastwest.dto;

import com.eastwest.model.WorkflowAction;
import com.eastwest.model.WorkflowCondition;
import com.eastwest.model.enums.workflow.WFMainCondition;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkflowPatchDTO {
    private String title;
    private WFMainCondition mainCondition;
    @ArraySchema(schema = @Schema(implementation = IdDTO.class))
    private List<WorkflowCondition> secondaryConditions;
    @Schema(implementation = IdDTO.class)
    private WorkflowAction action;
}
