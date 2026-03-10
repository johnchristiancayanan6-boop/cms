package com.eastwest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.eastwest.model.File;
import com.eastwest.model.PreventiveMaintenance;
import com.eastwest.model.WorkOrder;
import com.eastwest.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskShowDTO extends AuditShowDTO {
    private TaskBaseShowDTO taskBase;

    private String notes;

    private String value;

    private List<FileShowDTO> images = new ArrayList<>();
}


