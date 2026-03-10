package com.eastwest.mapper;

import com.eastwest.dto.FileShowDTO;
import com.eastwest.dto.MeterMiniDTO;
import com.eastwest.dto.MeterPatchDTO;
import com.eastwest.dto.MeterShowDTO;
import com.eastwest.model.Meter;
import com.eastwest.model.Reading;
import com.eastwest.service.ReadingService;
import com.eastwest.utils.AuditComparator;
import com.eastwest.utils.Helper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Mapper(componentModel = "spring", uses = {LocationMapper.class, AssetMapper.class, UserMapper.class, FileMapper.class})
public interface MeterMapper {
    Meter updateMeter(@MappingTarget Meter entity, MeterPatchDTO dto);

    MeterPatchDTO toPatchDto(Meter model);

    MeterShowDTO toShowDto(Meter model, @Context ReadingService readingService);

    @AfterMapping
    default MeterShowDTO toShowDto(Meter model, @MappingTarget MeterShowDTO target,
                                   @Context ReadingService readingService) {
        Collection<Reading> readings = readingService.findByMeter(target.getId());
        if (!readings.isEmpty()) {
            Reading lastReading = Collections.max(readings, new AuditComparator());
            target.setLastReading(lastReading.getCreatedAt());
            Date nextReading = Helper.incrementDays(lastReading.getCreatedAt(),
                    target.getUpdateFrequency());
            target.setNextReading(nextReading);
        }
        return target;
    }

    MeterMiniDTO toMiniDto(Meter model);
}
