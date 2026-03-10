package com.eastwest.mapper;

import com.eastwest.dto.WorkOrderHistoryShowDTO;
import com.eastwest.model.WorkOrderHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface WorkOrderHistoryMapper {
    @Mappings({})
    WorkOrderHistoryShowDTO toShowDto(WorkOrderHistory model);
}
