package com.eastwest.dto;

import com.eastwest.model.enums.PermissionEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RolePatchDTO {

    private String name;

    private String description;

    private String externalId;

    private List<PermissionEntity> createPermissions;
    private List<PermissionEntity> viewPermissions;

    private List<PermissionEntity> viewOtherPermissions;

    private List<PermissionEntity> editOtherPermissions;
    private List<PermissionEntity> deleteOtherPermissions;
}
