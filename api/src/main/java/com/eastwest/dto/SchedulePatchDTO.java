package com.eastwest.dto;

import com.eastwest.model.enums.RecurrenceBasedOn;
import com.eastwest.model.enums.RecurrenceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class SchedulePatchDTO {
    private Date startsOn;

    private int frequency;

    private Date endsOn;

    private Integer dueDateDelay;

    @NotNull
    private RecurrenceType recurrenceType;

    @NotNull
    private RecurrenceBasedOn recurrenceBasedOn;

    private List<Integer> daysOfWeek = new ArrayList<>();
}

