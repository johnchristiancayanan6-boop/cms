package com.eastwest.service;

import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.model.OwnUser;
import com.eastwest.model.WorkOrder;
import com.eastwest.model.WorkOrderHistory;
import com.eastwest.repository.WorkOrderAudRepository;
import com.eastwest.repository.WorkOrderHistoryRepository;
import com.eastwest.repository.WorkOrderRepository;
import com.eastwest.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkOrderHistoryService {
    private final WorkOrderHistoryRepository workOrderHistoryRepository;
    private final WorkOrderAudRepository workOrderAudRepository;
    private final WorkOrderRepository workOrderRepository;
    private final MessageSource messageSource;
    private final LicenseService licenseService;

    public WorkOrderHistory create(WorkOrderHistory workOrderHistory) {
        return workOrderHistoryRepository.save(workOrderHistory);
    }

    public WorkOrderHistory update(WorkOrderHistory workOrderHistory) {
        return workOrderHistoryRepository.save(workOrderHistory);
    }

    public Collection<WorkOrderHistory> getAll() {
        return workOrderHistoryRepository.findAll();
    }

    public void delete(Long id) {
        workOrderHistoryRepository.deleteById(id);
    }

    public Optional<WorkOrderHistory> findById(Long id) {
        return workOrderHistoryRepository.findById(id);
    }

    public Collection<WorkOrderHistory> findByWorkOrder(Long id) {
        // License check removed - all features are now free
        return workOrderAudRepository.findByIdAndRevtype(id, 1).stream().map(workOrderAud -> {
            WorkOrder workOrder = workOrderRepository.findById(id).get();
            OwnUser user = workOrderAud.getWorkOrderAudId().getRev().getUser();
            WorkOrderHistory workOrderHistory = WorkOrderHistory.builder()
                    .workOrder(workOrder)
                    .name(workOrderAud.getSummary(messageSource, Helper.getLocale(user)))
                    .user(user)
                    .build();
            workOrderHistory.setCreatedAt(new Date(workOrderAud.getWorkOrderAudId().getRev().getTimestamp()));
            return workOrderHistory;
        }).collect(Collectors.toList());
    }
}
