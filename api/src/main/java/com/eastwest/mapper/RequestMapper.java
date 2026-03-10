package com.eastwest.mapper;

import com.eastwest.dto.RequestPatchDTO;
import com.eastwest.dto.RequestShowDTO;
import com.eastwest.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {FileMapper.class})
public interface RequestMapper {
    Request updateRequest(@MappingTarget Request entity, RequestPatchDTO dto);

    @Mappings({})
    RequestPatchDTO toPatchDto(Request model);

    RequestShowDTO toShowDto(Request model);
}
