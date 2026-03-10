package com.eastwest.mapper;

import com.eastwest.dto.TaskBasePatchDTO;
import com.eastwest.dto.TaskBaseShowDTO;
import com.eastwest.model.TaskBase;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {TaskOptionMapper.class})
public interface TaskBaseMapper {
    TaskBase updateTaskBase(@MappingTarget TaskBase entity, TaskBasePatchDTO dto);

    @Mappings({})
    TaskBasePatchDTO toPatchDto(TaskBase model);

    TaskBaseShowDTO toShowDto(TaskBase model);
}
