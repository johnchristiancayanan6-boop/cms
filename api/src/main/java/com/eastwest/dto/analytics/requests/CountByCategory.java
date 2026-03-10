package com.eastwest.dto.analytics.requests;

import com.eastwest.dto.CategoryMiniDTO;
import com.eastwest.dto.UserMiniDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountByCategory extends CategoryMiniDTO {
    private int count;
}
