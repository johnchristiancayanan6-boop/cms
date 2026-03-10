package com.eastwest.controller;

import com.eastwest.exception.CustomException;
import com.eastwest.model.OwnUser;
import com.eastwest.model.WorkOrderRequestConfiguration;
import com.eastwest.service.UserService;
import com.eastwest.service.WorkOrderRequestConfigurationService;
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
@RequestMapping("/work-order-request-configurations")
@Tag(name = "workOrderRequestConfiguration")
@RequiredArgsConstructor
public class WorkOrderRequestConfigurationController {

    private final WorkOrderRequestConfigurationService workOrderRequestConfigurationService;
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public WorkOrderRequestConfiguration getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<WorkOrderRequestConfiguration> optionalWorkOrderRequestConfiguration =
                workOrderRequestConfigurationService.findById(id);
        if (optionalWorkOrderRequestConfiguration.isPresent()) {
            WorkOrderRequestConfiguration savedWorkOrderRequestConfiguration =
                    optionalWorkOrderRequestConfiguration.get();
            return savedWorkOrderRequestConfiguration;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

}

