package com.eastwest.mapper;

import com.eastwest.dto.ChecklistMiniDTO;
import com.eastwest.model.Checklist;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChecklistMapper {
    ChecklistMiniDTO toMiniDto(Checklist model);
}
