package com.eastwest.dto.analytics.assets;

import com.eastwest.dto.AssetMiniDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepairTimeByAsset extends AssetMiniDTO {
    //days
    private long duration;
}
