package com.eastwest.mapper;

import com.eastwest.dto.AssetShowDTO;
import com.eastwest.dto.FileShowDTO;
import com.eastwest.dto.LocationMiniDTO;
import com.eastwest.dto.LocationPatchDTO;
import com.eastwest.dto.LocationShowDTO;
import com.eastwest.model.Asset;
import com.eastwest.model.Location;
import com.eastwest.service.AssetService;
import com.eastwest.service.LocationService;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, VendorMapper.class, UserMapper.class,
        TeamMapper.class, FileMapper.class})
public interface LocationMapper {
    Location updateLocation(@MappingTarget Location entity, LocationPatchDTO dto);

    @Mappings({})
    LocationPatchDTO toPatchDto(Location model);

    LocationShowDTO toShowDto(Location model, @Context LocationService locationService);

    @Mapping(source = "parentLocation.id", target = "parentId")
    LocationMiniDTO toMiniDto(Location model);

    @AfterMapping
    default LocationShowDTO toShowDto(Location model, @MappingTarget LocationShowDTO target,
                                      @Context LocationService locationService) {
        target.setHasChildren(locationService.hasChildren(model.getId()));
        return target;
    }
}
