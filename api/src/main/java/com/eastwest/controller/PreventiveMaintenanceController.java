package com.eastwest.controller;

import com.eastwest.advancedsearch.SearchCriteria;
import com.eastwest.dto.PreventiveMaintenancePatchDTO;
import com.eastwest.dto.PreventiveMaintenancePostDTO;
import com.eastwest.dto.PreventiveMaintenanceShowDTO;
import com.eastwest.dto.SuccessResponse;
import com.eastwest.dto.workOrder.WorkOrderMiniDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.PreventiveMaintenanceMapper;
import com.eastwest.mapper.WorkOrderMapper;
import com.eastwest.model.OwnUser;
import com.eastwest.model.PreventiveMaintenance;
import com.eastwest.model.Schedule;
import com.eastwest.model.enums.PermissionEntity;
import com.eastwest.model.enums.RoleType;
import com.eastwest.service.PreventiveMaintenanceService;
import com.eastwest.service.ScheduleService;
import com.eastwest.service.UserService;
import com.eastwest.service.WorkOrderService;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/preventive-maintenances")
@Tag(name = "preventiveMaintenance")
@RequiredArgsConstructor
public class PreventiveMaintenanceController {

    private final PreventiveMaintenanceService preventiveMaintenanceService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final PreventiveMaintenanceMapper preventiveMaintenanceMapper;
    private final WorkOrderService workOrderService;
    private final WorkOrderMapper workOrderMapper;
    private final EntityManager em;

    @PostMapping("/search")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Page<PreventiveMaintenanceShowDTO>> search(@RequestBody SearchCriteria searchCriteria,
                                                                     HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getRole().getRoleType().equals(RoleType.ROLE_CLIENT)) {
            if (user.getRole().getViewPermissions().contains(PermissionEntity.PREVENTIVE_MAINTENANCES)) {
                searchCriteria.filterCompany(user);
            } else throw new CustomException("Access Denied", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(preventiveMaintenanceService.findBySearchCriteria(searchCriteria));
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public PreventiveMaintenanceShowDTO getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<PreventiveMaintenance> optionalPreventiveMaintenance = preventiveMaintenanceService.findById(id);
        if (optionalPreventiveMaintenance.isPresent()) {
            PreventiveMaintenance savedPreventiveMaintenance = optionalPreventiveMaintenance.get();
            return preventiveMaintenanceMapper.toShowDto(savedPreventiveMaintenance);
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/recent-work-orders")
    @PreAuthorize("permitAll()")
    public List<WorkOrderMiniDTO> getRecentWorkOrders(@PathVariable("id") Long id,
                                                      HttpServletRequest req) {
        return workOrderService.findLastByPM(id, 10).stream()
                .map(workOrderMapper::toMiniDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    PreventiveMaintenanceShowDTO create(@Valid @RequestBody PreventiveMaintenancePostDTO preventiveMaintenancePost,
                                        HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        PreventiveMaintenance preventiveMaintenance = preventiveMaintenanceMapper.toModel(preventiveMaintenancePost);
        preventiveMaintenance = preventiveMaintenanceService.create(preventiveMaintenance, user);

        Schedule schedule = preventiveMaintenance.getSchedule();
        schedule.setDaysOfWeek(preventiveMaintenancePost.getDaysOfWeek());
        schedule.setRecurrenceBasedOn(preventiveMaintenancePost.getRecurrenceBasedOn());
        schedule.setRecurrenceType(preventiveMaintenancePost.getRecurrenceType());
        schedule.setEndsOn(preventiveMaintenancePost.getEndsOn());
        schedule.setStartsOn(preventiveMaintenancePost.getStartsOn() != null ?
                preventiveMaintenancePost.getStartsOn() : new Date());
        schedule.setFrequency(preventiveMaintenancePost.getFrequency());
        schedule.setDueDateDelay(preventiveMaintenancePost.getDueDateDelay());
        Schedule savedSchedule = scheduleService.save(schedule);
        em.refresh(savedSchedule);
        em.refresh(preventiveMaintenance);
        scheduleService.scheduleWorkOrder(savedSchedule);
        return preventiveMaintenanceMapper.toShowDto(preventiveMaintenance);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public PreventiveMaintenanceShowDTO patch(@Valid @RequestBody PreventiveMaintenancePatchDTO preventiveMaintenance
            , @PathVariable("id") Long id,
                                              HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<PreventiveMaintenance> optionalPreventiveMaintenance = preventiveMaintenanceService.findById(id);

        if (optionalPreventiveMaintenance.isPresent()) {
            PreventiveMaintenance savedPreventiveMaintenance = optionalPreventiveMaintenance.get();
            PreventiveMaintenance patchedPreventiveMaintenance = preventiveMaintenanceService.update(id,
                    preventiveMaintenance, user);
            return preventiveMaintenanceMapper.toShowDto(patchedPreventiveMaintenance);
        } else throw new CustomException("PreventiveMaintenance not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public ResponseEntity delete(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<PreventiveMaintenance> optionalPreventiveMaintenance = preventiveMaintenanceService.findById(id);
        if (optionalPreventiveMaintenance.isPresent()) {
            scheduleService.stopScheduleJobs(optionalPreventiveMaintenance.get().getSchedule().getId());
            preventiveMaintenanceService.delete(id);
            return new ResponseEntity(new SuccessResponse(true, "Deleted successfully"),
                    HttpStatus.OK);
        } else throw new CustomException("PreventiveMaintenance not found", HttpStatus.NOT_FOUND);
    }

}



