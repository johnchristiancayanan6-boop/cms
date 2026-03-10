package com.eastwest.dto.analytics.workOrders;

import com.eastwest.dto.UserMiniDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WOCountByUser extends UserMiniDTO {
    private int count;
}
