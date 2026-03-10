package com.eastwest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskOptionShowDTO extends AuditShowDTO {
    private String label;
}
