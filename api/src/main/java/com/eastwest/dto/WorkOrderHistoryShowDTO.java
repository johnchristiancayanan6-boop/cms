package com.eastwest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WorkOrderHistoryShowDTO extends AuditShowDTO {
    
    private String name;

    private UserMiniDTO user;

}
