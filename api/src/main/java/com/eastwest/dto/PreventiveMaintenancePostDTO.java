package com.eastwest.dto;

import com.eastwest.model.abstracts.WorkOrderBase;
import com.eastwest.model.enums.RecurrenceBasedOn;
import com.eastwest.model.enums.RecurrenceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PreventiveMaintenancePostDTO extends WorkOrderBase {
    private Date startsOn;

    @NotNull
    private String name;

    @NotNull
    private int frequency;

    private Integer dueDateDelay;

    private Date endsOn;

    @NotNull
    private RecurrenceType recurrenceType;

    @NotNull
    private RecurrenceBasedOn recurrenceBasedOn;

    private List<Integer> daysOfWeek = new ArrayList<>();
}


