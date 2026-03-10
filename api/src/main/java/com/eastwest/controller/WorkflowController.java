package com.eastwest.controller;

import com.eastwest.dto.SuccessResponse;
import com.eastwest.dto.WorkflowPostDTO;
import com.eastwest.dto.WorkflowShowDTO;
import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.WorkflowActionMapper;
import com.eastwest.mapper.WorkflowConditionMapper;
import com.eastwest.mapper.WorkflowMapper;
import com.eastwest.model.*;
import com.eastwest.model.enums.PermissionEntity;
import com.eastwest.model.enums.PlanFeatures;
import com.eastwest.model.enums.RoleType;
import com.eastwest.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workflows")
@Tag(name = "workflow")
@RequiredArgsConstructor
public class WorkflowController {

    private final WorkflowService workflowService;
    private final UserService userService;
    private final WorkflowConditionMapper workflowConditionMapper;
    private final WorkflowConditionService workflowConditionService;
    private final WorkflowActionMapper workflowActionMapper;
    private final WorkflowActionService workflowActionService;
    private final LicenseService licenseService;
    private final WorkflowMapper workflowMapper;

    @GetMapping("")
    @PreAuthorize("permitAll()")

    public Collection<WorkflowShowDTO> getAll(HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getRoleType().equals(RoleType.ROLE_CLIENT)) {
            return workflowService.findByCompany(user.getCompany().getId()).stream().map(workflowMapper::toShowDto).collect(Collectors.toList());
        } else
            return workflowService.getAll().stream().map(workflowMapper::toShowDto).collect(Collectors.toList());
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public WorkflowShowDTO create(@Valid @RequestBody WorkflowPostDTO workflowReq,
                                  HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getViewPermissions().contains(PermissionEntity.SETTINGS)) {
            int workflowsCount =
                    (int) workflowService.findByCompany(user.getCompany().getId()).stream().filter(Workflow::isEnabled).count();
            // License check removed - all features are now free
            if (user.getCompany().getSubscription().getSubscriptionPlan().getFeatures().contains(PlanFeatures.WORKFLOW) || workflowsCount == 0) {
                return workflowMapper.toShowDto(createWorkflow(workflowReq, user.getCompany()));
            } else
                throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
        } else throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public WorkflowShowDTO getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Workflow> optionalWorkflow = workflowService.findById(id);
        if (optionalWorkflow.isPresent()) {
            Workflow savedWorkflow = optionalWorkflow.get();
            return workflowMapper.toShowDto(savedWorkflow);
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public WorkflowShowDTO patch(@Valid @RequestBody WorkflowPostDTO workflow,
                                 @PathVariable("id") Long id,
                                 HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Workflow> optionalWorkflow = workflowService.findById(id);

        if (optionalWorkflow.isPresent()) {
            Workflow savedWorkflow = optionalWorkflow.get();
            workflowService.delete(id);
            return workflowMapper.toShowDto(createWorkflow(workflow, user.getCompany()));
        } else throw new CustomException("Workflow not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public ResponseEntity<SuccessResponse> delete(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<Workflow> optionalWorkflow = workflowService.findById(id);
        if (optionalWorkflow.isPresent()) {
            Workflow savedWorkflow = optionalWorkflow.get();
            workflowService.delete(id);
            return new ResponseEntity(new SuccessResponse(true, "Deleted successfully"),
                    HttpStatus.OK);
        } else throw new CustomException("Workflow not found", HttpStatus.NOT_FOUND);
    }

    private Workflow createWorkflow(WorkflowPostDTO workflowReq, Company company) {
        List<WorkflowCondition> workflowConditions =
                workflowReq.getSecondaryConditions().stream().map(workflowConditionMapper::toModel)
                        .collect(Collectors.toList());
        Collection<WorkflowCondition> savedWorkOrderConditions = workflowConditionService.saveAll(workflowConditions);
        WorkflowAction workflowAction = workflowActionMapper.toModel(workflowReq.getAction());
        WorkflowAction savedWorkflowAction = workflowActionService.create(workflowAction);
        Workflow workflow = Workflow.builder()
                .title(workflowReq.getTitle())
                .mainCondition(workflowReq.getMainCondition())
                .secondaryConditions(savedWorkOrderConditions)
                .action(savedWorkflowAction)
                .enabled(true)
                .build();
        return workflowService.create(workflow);
    }
}


