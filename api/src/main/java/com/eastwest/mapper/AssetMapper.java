package com.eastwest.mapper;

import com.eastwest.dto.AssetMiniDTO;
import com.eastwest.dto.AssetPatchDTO;
import com.eastwest.dto.AssetShowDTO;
import com.eastwest.dto.MeterShowDTO;
import com.eastwest.model.Asset;
import com.eastwest.model.Meter;
import com.eastwest.model.Reading;
import com.eastwest.service.AssetService;
import com.eastwest.service.ReadingService;
import com.eastwest.utils.AuditComparator;
import com.eastwest.utils.Helper;
import org.mapstruct.*;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, VendorMapper.class, UserMapper.class,
        TeamMapper.class, FileMapper.class, PartMapper.class, FileMapper.class})
public interface AssetMapper {
    Asset updateAsset(@MappingTarget Asset entity, AssetPatchDTO dto);

    @Mappings({})
    AssetPatchDTO toPatchDto(Asset model);

    AssetShowDTO toShowDto(Asset model, @Context AssetService assetService);

    @Mapping(target = "parentId", source = "parentAsset.id")
    @Mapping(target = "locationId", source = "location.id")
    AssetMiniDTO toMiniDto(Asset model);

    @AfterMapping
    default AssetShowDTO toShowDto(Asset model, @MappingTarget AssetShowDTO target,
                                   @Context AssetService assetService) {
        target.setHasChildren(assetService.hasChildren(model.getId()));
        return target;
    }
}
