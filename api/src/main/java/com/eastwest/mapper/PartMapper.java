package com.eastwest.mapper;

import com.eastwest.dto.PartMiniDTO;
import com.eastwest.dto.PartPatchDTO;
import com.eastwest.dto.PartShowDTO;
import com.eastwest.dto.FileShowDTO;
import com.eastwest.model.Part;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, VendorMapper.class, UserMapper.class, TeamMapper.class, FileMapper.class})
public interface PartMapper {
    Part updatePart(@MappingTarget Part entity, PartPatchDTO dto);

    @Mappings({})
    PartPatchDTO toPatchDto(Part model);

    @Mappings({})
    PartMiniDTO toMiniDto(Part model);

    PartShowDTO toShowDto(Part model);
}
