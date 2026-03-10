package com.eastwest.controller;

import com.eastwest.exception.CustomException;
import com.eastwest.model.OwnUser;
import com.eastwest.model.WorkOrderConfiguration;
import com.eastwest.service.UserService;
import com.eastwest.service.WorkOrderConfigurationService;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@RestController
@RequestMapping("/work-order-configurations")
@Tag(name = "workOrderConfiguration")
@RequiredArgsConstructor
public class WorkOrderConfigurationController {

    private final WorkOrderConfigurationService workOrderConfigurationService;
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public WorkOrderConfiguration getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<WorkOrderConfiguration> optionalWorkOrderConfiguration = workOrderConfigurationService.findById(id);
        if (optionalWorkOrderConfiguration.isPresent()) {
            WorkOrderConfiguration savedWorkOrderConfiguration = optionalWorkOrderConfiguration.get();
            return savedWorkOrderConfiguration;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

}

