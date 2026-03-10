package com.eastwest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PreventiveMaintenancePatchDTO extends WorkOrderBasePatchDTO {
    private String name;
}
