package com.eastwest.dto;

import com.eastwest.model.CompanySettings;
import com.eastwest.model.Subscription;
import lombok.Data;

@Data
public class CompanyShowDTO extends AuditShowDTO {
    private String name;
    private String address;
    private String phone;
    private String website;
    private String email;

    private int employeesCount;

    private FileShowDTO logo;

    private String city;

    private String state;

    private String zipCode;

    private Subscription subscription;

    private CompanySettings companySettings;

    private boolean demo;
}
