package com.eastwest.mapper;

import com.eastwest.dto.CompanyPatchDTO;
import com.eastwest.dto.CompanyShowDTO;
import com.eastwest.model.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {FileMapper.class})
public interface CompanyMapper {
    Company updateCompany(@MappingTarget Company entity, CompanyPatchDTO dto);

    @Mappings({})
    CompanyPatchDTO toPatchDto(Company model);

    CompanyShowDTO toShowDto(Company model);


}
