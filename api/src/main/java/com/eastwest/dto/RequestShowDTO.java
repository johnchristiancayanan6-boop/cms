package com.eastwest.dto;

import com.eastwest.dto.workOrder.WorkOrderMiniDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestShowDTO extends WorkOrderBaseShowDTO {
    private boolean cancelled;

    private String cancellationReason;

    private WorkOrderMiniDTO workOrder;

    private FileMiniDTO audioDescription;

    private String customId;
}
