package com.eastwest.mapper;

import com.eastwest.dto.TeamMiniDTO;
import com.eastwest.dto.TeamPatchDTO;
import com.eastwest.dto.TeamShowDTO;
import com.eastwest.model.Team;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TeamMapper {
    Team updateTeam(@MappingTarget Team entity, TeamPatchDTO dto);

    @Mappings({})
    TeamPatchDTO toPatchDto(Team model);

    TeamMiniDTO toMiniDto(Team model);

    TeamShowDTO toShowDto(Team model);
}
