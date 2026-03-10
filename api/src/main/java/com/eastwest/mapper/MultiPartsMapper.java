package com.eastwest.mapper;

import com.eastwest.dto.MultiPartsMiniDTO;
import com.eastwest.dto.MultiPartsPatchDTO;
import com.eastwest.dto.MultiPartsShowDTO;
import com.eastwest.model.MultiParts;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {PartMapper.class})
public interface MultiPartsMapper {
    MultiParts updateMultiParts(@MappingTarget MultiParts entity, MultiPartsPatchDTO dto);

    @Mappings({})
    MultiPartsPatchDTO toPatchDto(MultiParts model);

    MultiPartsShowDTO toShowDto(MultiParts model);

    MultiPartsMiniDTO toMiniDto(MultiParts multiParts);
}
