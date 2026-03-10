package com.eastwest.controller;

import com.eastwest.dto.WorkOrderHistoryShowDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.WorkOrderHistoryMapper;
import com.eastwest.model.OwnUser;
import com.eastwest.model.WorkOrder;
import com.eastwest.model.WorkOrderHistory;
import com.eastwest.service.UserService;
import com.eastwest.service.WorkOrderHistoryService;
import com.eastwest.service.WorkOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/work-order-histories")
@Tag(name = "workOrderHistory")
@RequiredArgsConstructor
public class WorkOrderHistoryController {

    private final WorkOrderHistoryService workOrderHistoryService;
    private final UserService userService;
    private final WorkOrderService workOrderService;
    private final WorkOrderHistoryMapper workOrderHistoryMapper;

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public WorkOrderHistoryShowDTO getById(@PathVariable("id") Long id, HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<WorkOrderHistory> optionalWorkOrderHistory = workOrderHistoryService.findById(id);
        if (optionalWorkOrderHistory.isPresent()) {
            WorkOrderHistory savedWorkOrderHistory = optionalWorkOrderHistory.get();
            return workOrderHistoryMapper.toShowDto(savedWorkOrderHistory);
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/work-order/{id}")
    @PreAuthorize("permitAll()")

    public Collection<WorkOrderHistoryShowDTO> getByWorkOrder(@PathVariable("id") Long id,
                                                              HttpServletRequest req) {
        OwnUser user = userService.whoami(req);
        Optional<WorkOrder> optionalWorkOrder = workOrderService.findById(id);
        if (optionalWorkOrder.isPresent()) {
            return workOrderHistoryService.findByWorkOrder(id).stream().map(workOrderHistoryMapper::toShowDto).collect(Collectors.toList());
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

}

