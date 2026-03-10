package com.eastwest.dto;

import com.eastwest.model.enums.ApprovalStatus;
import com.eastwest.model.enums.Priority;
import com.eastwest.model.enums.Status;
import com.eastwest.model.enums.workflow.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class WorkflowConditionShowDTO extends AuditShowDTO {
    private WorkOrderCondition workOrderCondition;
    private RequestCondition requestCondition;
    private PurchaseOrderCondition purchaseOrderCondition;
    private PartCondition partCondition;
    private TaskCondition taskCondition;
    private Priority priority;
    private AssetMiniDTO asset;
    private LocationMiniDTO location;
    private UserMiniDTO user;
    private TeamMiniDTO team;
    private VendorMiniDTO vendor;
    private PartMiniDTO part;
    private CategoryMiniDTO workOrderCategory;
    private CategoryMiniDTO purchaseOrderCategory;
    private Status workOrderStatus;
    private ApprovalStatus purchaseOrderStatus;
    private Integer createdTimeStart;
    private Integer createdTimeEnd;
    private Date startDate;
    private Date endDate;
    private String label;
    private String value;
    private Integer numberValue;
}
