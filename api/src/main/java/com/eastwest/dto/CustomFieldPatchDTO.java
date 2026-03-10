package com.eastwest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomFieldPatchDTO {
    
    private String name;

    private String value;
}
