package com.eastwest.mapper;

import com.eastwest.dto.SubscriptionPlanPatchDTO;
import com.eastwest.model.SubscriptionPlan;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SubscriptionPlanMapper {
    SubscriptionPlan updateSubscriptionPlan(@MappingTarget SubscriptionPlan entity, SubscriptionPlanPatchDTO dto);

    @Mappings({})
    SubscriptionPlanPatchDTO toPatchDto(SubscriptionPlan model);
}
