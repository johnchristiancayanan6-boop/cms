package com.eastwest.dto;

import com.eastwest.model.Company;
import com.eastwest.dto.FileShowDTO;
import com.eastwest.model.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LocationShowDTO extends AuditShowDTO {

    private String name;

    private String address;

    private Double longitude;

    private Double latitude;

    private boolean hasChildren;

    private List<TeamMiniDTO> teams = new ArrayList<>();

    private Location parentLocation;

    private List<VendorMiniDTO> vendors = new ArrayList<>();

    private List<CustomerMiniDTO> customers = new ArrayList<>();

    private List<UserMiniDTO> workers = new ArrayList<>();

    private FileShowDTO image;

    private List<FileMiniDTO> files;

    private String customId;

}
