package com.eastwest.dto;

import com.eastwest.model.SubscriptionPlan;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubscriptionPatchDTO {

    private int usersCount;

    private boolean monthly;

    @Schema(implementation = IdDTO.class)
    private SubscriptionPlan subscriptionPlan;
}
