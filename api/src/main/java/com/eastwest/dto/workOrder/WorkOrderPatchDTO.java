package com.eastwest.dto.workOrder;

import com.eastwest.dto.IdDTO;
import com.eastwest.dto.WorkOrderBasePatchDTO;
import com.eastwest.model.OwnUser;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class WorkOrderPatchDTO extends WorkOrderBasePatchDTO {
    @Schema(implementation = IdDTO.class)
    private OwnUser completedBy;
    private Date completedOn;
    private boolean archived;
}
