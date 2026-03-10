package com.eastwest.dto.workOrder;

import com.eastwest.model.WorkOrder;
import com.eastwest.model.enums.AssetStatus;
import lombok.Data;

@Data
public class WorkOrderPostDTO extends WorkOrder {
    private AssetStatus assetStatus;
}
