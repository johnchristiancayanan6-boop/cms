package com.eastwest.dto;

import com.eastwest.model.File;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class WorkOrderChangeStatusDTO {
    private Status status;
    private String signature;
    private String feedback;
}