package com.eastwest.controller;

import com.eastwest.dto.AdditionalCostPatchDTO;
import com.eastwest.dto.SuccessResponse;
import com.eastwest.exception.CustomException;
import com.eastwest.model.AdditionalCost;
import com.eastwest.model.OwnUser;
import com.eastwest.model.WorkOrder;
import com.eastwest.model.enums.PlanFeatures;
import com.eastwest.service.AdditionalCostService;
import com.eastwest.service.UserService;
import com.eastwest.service.WorkOrderService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/additional-costs")
@Tag(name = "additionalCost")
@RequiredArgsConstructor
public class AdditionalCostController {

    private final AdditionalCostService additionalCostService;
    private final UserService userService;
    private final WorkOrderService workOrderService;


    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public AdditionalCost getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<AdditionalCost> optionalAdditionalCost = additionalCostService.findById(id);
        if (optionalAdditionalCost.isPresent()) {
            AdditionalCost savedAdditionalCost = optionalAdditionalCost.get();
            return savedAdditionalCost;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/work-order/{id}")
    @PreAuthorize("permitAll()")
    public Collection<AdditionalCost> getByWorkOrder(@PathVariable("id") Long id,
                                                     HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<WorkOrder> optionalWorkOrder = workOrderService.findById(id);
        if (optionalWorkOrder.isPresent()) {
            return additionalCostService.findByWorkOrder(id);
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public AdditionalCost create(@Valid @RequestBody AdditionalCost additionalCostReq,
                                 HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        if (user.getCompany().getSubscription().getSubscriptionPlan().getFeatures().contains(PlanFeatures.ADDITIONAL_COST)) {
            WorkOrder workOrder = workOrderService.findById(additionalCostReq.getWorkOrder().getId()).get();
            if (workOrder.getFirstTimeToReact() == null) {
                workOrder.setFirstTimeToReact(new Date());
                workOrderService.save(workOrder);
            }
            return additionalCostService.create(additionalCostReq);
        } else throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public AdditionalCost patch(@Valid @RequestBody AdditionalCostPatchDTO additionalCost, @PathVariable("id") Long id,
                                HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<AdditionalCost> optionalAdditionalCost = additionalCostService.findById(id);

        if (optionalAdditionalCost.isPresent()) {
            AdditionalCost savedAdditionalCost = optionalAdditionalCost.get();
            return additionalCostService.update(id, additionalCost);
        } else throw new CustomException("AdditionalCost not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity delete(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);

        Optional<AdditionalCost> optionalAdditionalCost = additionalCostService.findById(id);
        if (optionalAdditionalCost.isPresent()) {
            AdditionalCost savedAdditionalCost = optionalAdditionalCost.get();
            additionalCostService.delete(id);
            return new ResponseEntity(new SuccessResponse(true, "Deleted successfully"),
                    HttpStatus.OK);
        } else throw new CustomException("AdditionalCost not found", HttpStatus.NOT_FOUND);
    }

}


