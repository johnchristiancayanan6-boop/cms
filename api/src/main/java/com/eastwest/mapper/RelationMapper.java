package com.eastwest.mapper;

import com.eastwest.dto.RelationPatchDTO;
import com.eastwest.dto.RelationPostDTO;
import com.eastwest.model.Relation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RelationMapper {
    Relation updateRelation(@MappingTarget Relation entity, RelationPatchDTO dto);

    @Mappings({})
    RelationPatchDTO toPatchDto(RelationPostDTO model);
}
