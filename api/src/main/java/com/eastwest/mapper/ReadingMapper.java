package com.eastwest.mapper;

import com.eastwest.dto.ReadingPatchDTO;
import com.eastwest.model.Reading;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReadingMapper {
    Reading updateReading(@MappingTarget Reading entity, ReadingPatchDTO dto);

    @Mappings({})
    ReadingPatchDTO toPatchDto(Reading model);
}
