package com.eastwest.dto;

import com.eastwest.model.enums.FieldType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FieldConfigurationPatchDTO {
    private FieldType fieldType;

}
