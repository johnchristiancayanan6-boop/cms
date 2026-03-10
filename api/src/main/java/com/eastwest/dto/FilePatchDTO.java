package com.eastwest.dto;

import com.eastwest.model.enums.FileType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class FilePatchDTO {
    @NotNull
    private String name;
}

