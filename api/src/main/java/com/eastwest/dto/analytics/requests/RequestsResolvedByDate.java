package com.eastwest.dto.analytics.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestsResolvedByDate {
    private int received;
    private int resolved;
    private Date date;
}
