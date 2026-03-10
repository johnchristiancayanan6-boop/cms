package com.eastwest.controller;

import com.eastwest.dto.FieldConfigurationPatchDTO;
import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.exception.CustomException;
import com.eastwest.model.FieldConfiguration;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.PermissionEntity;
import com.eastwest.model.enums.PlanFeatures;
import com.eastwest.service.FieldConfigurationService;
import com.eastwest.service.LicenseService;
import com.eastwest.service.UserService;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/field-configurations")
@Tag(name = "fieldConfiguration")
@RequiredArgsConstructor
public class FieldConfigurationController {

    private final FieldConfigurationService fieldConfigurationService;
    private final UserService userService;
    private final LicenseService licenseService;

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public FieldConfiguration patch(@Valid @RequestBody FieldConfigurationPatchDTO fieldConfiguration, @PathVariable(
                                            "id") Long id,
                                    HttpServletRequest req) {
        // License check removed - all features are now free
        OwnUser user = userService.whoami(req);
        Optional<FieldConfiguration> optionalFieldConfiguration = fieldConfigurationService.findById(id);

        if (optionalFieldConfiguration.isPresent()) {
            FieldConfiguration savedFieldConfiguration = optionalFieldConfiguration.get();
            if (user.getRole().getViewPermissions().contains(PermissionEntity.SETTINGS)
                    && user.getCompany().getSubscription().getSubscriptionPlan().getFeatures().contains(PlanFeatures.REQUEST_CONFIGURATION)) {
                return fieldConfigurationService.update(id, fieldConfiguration);
            } else throw new CustomException("Forbidden", HttpStatus.FORBIDDEN);
        } else throw new CustomException("FieldConfiguration not found", HttpStatus.NOT_FOUND);
    }


}


