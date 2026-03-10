package com.eastwest.dto.imports;

import com.eastwest.model.enums.RecurrenceBasedOn;
import com.eastwest.model.enums.RecurrenceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class PreventiveMaintenanceImportDTO extends WorkOrderImportDTO {
    private Double startsOn;

    @NotNull
    private String name;

    @NotNull
    private double frequency;

    private Double dueDateDelay;

    private Double endsOn;

    @NotNull
    private String recurrenceType;

    @NotNull
    private String recurrenceBasedOn;
    @Builder.Default
    private List<String> daysOfWeek = new ArrayList<>();
}

