package com.eastwest.dto;

import com.eastwest.model.Asset;
import com.eastwest.model.Meter;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.TaskType;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
public class TaskBaseDTO {
    @NotNull
    private String label;

    private TaskType taskType = TaskType.SUBTASK;

    private OwnUser user;

    private Asset asset;

    private Meter meter;

    private List<String> options;
}

