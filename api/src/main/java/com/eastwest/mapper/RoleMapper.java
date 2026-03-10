package com.eastwest.mapper;

import com.eastwest.dto.RolePatchDTO;
import com.eastwest.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role updateRole(@MappingTarget Role entity, RolePatchDTO dto);

    @Mappings({})
    RolePatchDTO toPatchDto(Role model);
}
