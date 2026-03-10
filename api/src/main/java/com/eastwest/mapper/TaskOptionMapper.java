package com.eastwest.mapper;

import com.eastwest.dto.TaskOptionPatchDTO;
import com.eastwest.dto.TaskOptionShowDTO;
import com.eastwest.model.TaskOption;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TaskOptionMapper {
    TaskOption updateTaskOption(@MappingTarget TaskOption entity, TaskOptionPatchDTO dto);

    @Mappings({})
    TaskOptionPatchDTO toPatchDto(TaskOption model);

    TaskOptionShowDTO toShowDto(TaskOption model);
}
