package com.eastwest.mapper;

import com.eastwest.dto.TaskPatchDTO;
import com.eastwest.dto.TaskShowDTO;
import com.eastwest.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {FileMapper.class, TaskBaseMapper.class})
public interface TaskMapper {
    Task updateTask(@MappingTarget Task entity, TaskPatchDTO dto);

    @Mappings({})
    TaskPatchDTO toPatchDto(Task model);

    TaskShowDTO toShowDto(Task model);
}
