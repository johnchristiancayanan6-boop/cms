package com.eastwest.dto;

import com.eastwest.model.File;
import lombok.Data;

@Data
public class SuperAccountRelationDTO {
    private String childCompanyName;
    private Long childUserId;
    private Long superUserId;
}
