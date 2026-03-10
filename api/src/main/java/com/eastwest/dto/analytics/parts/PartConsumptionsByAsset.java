package com.eastwest.dto.analytics.parts;

import com.eastwest.dto.AssetMiniDTO;
import com.eastwest.dto.PartMiniDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PartConsumptionsByAsset extends AssetMiniDTO {
    private double cost;
}
