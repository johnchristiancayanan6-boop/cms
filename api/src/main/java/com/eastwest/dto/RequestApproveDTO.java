package com.eastwest.dto;

import com.eastwest.model.enums.AssetStatus;
import lombok.Data;

@Data
public class RequestApproveDTO {
    private AssetStatus assetStatus;
}
