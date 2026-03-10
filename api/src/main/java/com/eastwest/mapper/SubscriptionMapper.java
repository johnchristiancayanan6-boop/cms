package com.eastwest.mapper;

import com.eastwest.dto.SubscriptionPatchDTO;
import com.eastwest.model.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    Subscription updateSubscription(@MappingTarget Subscription entity, SubscriptionPatchDTO dto);

    @Mappings({})
    SubscriptionPatchDTO toPatchDto(Subscription model);
}
