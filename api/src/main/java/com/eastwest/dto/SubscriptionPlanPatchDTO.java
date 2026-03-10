package com.eastwest.dto;

import com.eastwest.model.enums.PlanFeatures;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
public class SubscriptionPlanPatchDTO {

    private String name;

    private double monthlyCostPerUser;

    private double yearlyCostPerUser;

    private String code;

    private Collection<PlanFeatures> features;

}
