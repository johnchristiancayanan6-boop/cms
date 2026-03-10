package com.eastwest.dto.analytics.workOrders;

import com.eastwest.dto.CategoryMiniDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WOCountByCategory extends CategoryMiniDTO {
    private int count;
}
