package com.eastwest.mapper;

import com.eastwest.dto.SchedulePatchDTO;
import com.eastwest.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ScheduleMapper {
    Schedule updateSchedule(@MappingTarget Schedule entity, SchedulePatchDTO dto);

    @Mappings({})
    SchedulePatchDTO toPatchDto(Schedule model);
}
