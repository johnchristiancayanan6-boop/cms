package com.eastwest.mapper;

import com.eastwest.dto.GeneralPreferencesPatchDTO;
import com.eastwest.model.GeneralPreferences;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GeneralPreferencesMapper {
    GeneralPreferences updateGeneralPreferences(@MappingTarget GeneralPreferences entity, GeneralPreferencesPatchDTO dto);

    @Mappings({})
    GeneralPreferencesPatchDTO toPatchDto(GeneralPreferences model);
}
