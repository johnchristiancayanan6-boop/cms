package com.eastwest.dto;

import com.eastwest.model.File;
import com.eastwest.model.Role;
import com.eastwest.model.SuperAccountRelation;
import com.eastwest.model.UiConfiguration;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserResponseDTO {

    private Integer id;
    private String username;
    private String email;
    private Role role;

    private long rate;
    private String jobTitle;

    private String firstName;

    private String lastName;

    private String phone;

    private boolean ownsCompany;

    private Long companyId;

    private Long companySettingsId;

    private Long userSettingsId;

    private FileShowDTO image;

    private List<SuperAccountRelationDTO> superAccountRelations = new ArrayList<>();

    private SuperAccountRelationDTO parentSuperAccount;

    private Boolean enabled;

    private Boolean enabledInSubscription;

    private UiConfiguration uiConfiguration;

    private Date lastLogin;

    private Date createdAt;

    private String paddleUserId;

}
