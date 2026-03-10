package com.eastwest.mapper;

import com.eastwest.dto.VendorMiniDTO;
import com.eastwest.dto.VendorPatchDTO;
import com.eastwest.model.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    Vendor updateVendor(@MappingTarget Vendor entity, VendorPatchDTO dto);

    @Mappings({})
    VendorPatchDTO toPatchDto(Vendor model);

    VendorMiniDTO toMiniDto(Vendor model);
}
