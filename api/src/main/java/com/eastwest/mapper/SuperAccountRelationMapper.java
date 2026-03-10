package com.eastwest.mapper;

import com.eastwest.dto.RolePatchDTO;
import com.eastwest.dto.SuperAccountRelationDTO;
import com.eastwest.model.Role;
import com.eastwest.model.SuperAccountRelation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SuperAccountRelationMapper {

    @Mappings({
            @Mapping(source = "childUser.company.name", target = "childCompanyName"),
            @Mapping(source = "childUser.id", target = "childUserId"),
            @Mapping(source = "superUser.id", target = "superUserId"),
    })
    SuperAccountRelationDTO toDto(SuperAccountRelation model);
}
