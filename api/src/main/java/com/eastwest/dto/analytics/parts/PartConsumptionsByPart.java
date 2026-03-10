package com.eastwest.dto.analytics.parts;

import com.eastwest.dto.PartMiniDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartConsumptionsByPart extends PartMiniDTO {
    private double cost;
}
