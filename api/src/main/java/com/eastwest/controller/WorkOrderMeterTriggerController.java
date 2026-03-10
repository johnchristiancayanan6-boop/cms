package com.eastwest.controller;

import com.eastwest.dto.SuccessResponse;
import com.eastwest.dto.WorkOrderMeterTriggerPatchDTO;
import com.eastwest.dto.WorkOrderMeterTriggerShowDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.WorkOrderMeterTriggerMapper;
import com.eastwest.model.Meter;
import com.eastwest.model.OwnUser;
import com.eastwest.model.WorkOrderMeterTrigger;
import com.eastwest.service.MeterService;
import com.eastwest.service.UserService;
import com.eastwest.service.WorkOrderMeterTriggerService;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/work-order-meter-triggers")
@Tag(name = "workOrderMeterTrigger")
@RequiredArgsConstructor
public class WorkOrderMeterTriggerController {

    private final WorkOrderMeterTriggerService workOrderMeterTriggerService;
    private final WorkOrderMeterTriggerMapper workOrderMeterTriggerMapper;
    private final UserService userService;
    private final MeterService meterService;


    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")

    public WorkOrderMeterTrigger getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<WorkOrderMeterTrigger> optionalWorkOrderMeterTrigger = workOrderMeterTriggerService.findById(id);
        if (optionalWorkOrderMeterTrigger.isPresent()) {
            WorkOrderMeterTrigger savedWorkOrderMeterTrigger = optionalWorkOrderMeterTrigger.get();
            return savedWorkOrderMeterTrigger;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    WorkOrderMeterTrigger create(@Valid @RequestBody WorkOrderMeterTrigger workOrderMeterTriggerReq,
                                 HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        return workOrderMeterTriggerService.create(workOrderMeterTriggerReq);
    }


    @GetMapping("/meter/{id}")
    @PreAuthorize("permitAll()")

    public Collection<WorkOrderMeterTriggerShowDTO> getByMeter(@PathVariable("id") Long id,
                                                               HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<Meter> optionalMeter = meterService.findById(id);
        if (optionalMeter.isPresent()) {
            return workOrderMeterTriggerService.findByMeter(id).stream().map(workOrderMeterTriggerMapper::toShowDto).collect(Collectors.toList());
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }


    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public WorkOrderMeterTriggerShowDTO patch(@Valid @RequestBody WorkOrderMeterTriggerPatchDTO workOrderMeterTrigger
            , @PathVariable("id") Long id,
                                              HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<WorkOrderMeterTrigger> optionalWorkOrderMeterTrigger = workOrderMeterTriggerService.findById(id);

        if (optionalWorkOrderMeterTrigger.isPresent()) {
            WorkOrderMeterTrigger savedWorkOrderMeterTrigger = optionalWorkOrderMeterTrigger.get();
            return workOrderMeterTriggerMapper.toShowDto(workOrderMeterTriggerService.update(id,
                    workOrderMeterTrigger));
        } else throw new CustomException("WorkOrderMeterTrigger not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")

    public ResponseEntity delete(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<WorkOrderMeterTrigger> optionalWorkOrderMeterTrigger = workOrderMeterTriggerService.findById(id);
        if (optionalWorkOrderMeterTrigger.isPresent()) {
            WorkOrderMeterTrigger savedWorkOrderMeterTrigger = optionalWorkOrderMeterTrigger.get();
            workOrderMeterTriggerService.delete(id);
            return new ResponseEntity(new SuccessResponse(true, "Deleted successfully"),
                    HttpStatus.OK);
        } else throw new CustomException("WorkOrderMeterTrigger not found", HttpStatus.NOT_FOUND);
    }

}


