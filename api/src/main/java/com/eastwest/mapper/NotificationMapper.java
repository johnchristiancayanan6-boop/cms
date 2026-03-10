package com.eastwest.mapper;

import com.eastwest.dto.NotificationPatchDTO;
import com.eastwest.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    Notification updateNotification(@MappingTarget Notification entity, NotificationPatchDTO dto);

    @Mappings({})
    NotificationPatchDTO toPatchDto(Notification model);
}
