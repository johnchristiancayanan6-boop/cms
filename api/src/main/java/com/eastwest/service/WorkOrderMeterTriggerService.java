package com.eastwest.service;

import com.eastwest.dto.WorkOrderMeterTriggerPatchDTO;
import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.WorkOrderMeterTriggerMapper;
import com.eastwest.model.WorkOrderMeterTrigger;
import com.eastwest.repository.WorkOrderMeterTriggerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkOrderMeterTriggerService {
    private final WorkOrderMeterTriggerRepository workOrderMeterTriggerRepository;
    private final WorkOrderService workOrderService;
    private final WorkOrderMeterTriggerMapper workOrderMeterTriggerMapper;
    private final MeterService meterService;
    private final EntityManager em;
    private final LicenseService licenseService;

    @Transactional
    public WorkOrderMeterTrigger create(WorkOrderMeterTrigger workOrderMeterTrigger) {
        // LICENSE REMOVED: No longer checking CONDITION_BASED_PM entitlement
        WorkOrderMeterTrigger savedWorkOrderMeterTrigger =
                workOrderMeterTriggerRepository.saveAndFlush(workOrderMeterTrigger);
        em.refresh(savedWorkOrderMeterTrigger);
        return savedWorkOrderMeterTrigger;
    }

    @Transactional
    public WorkOrderMeterTrigger update(Long id, WorkOrderMeterTriggerPatchDTO workOrderMeterTrigger) {
        if (workOrderMeterTriggerRepository.existsById(id)) {
            WorkOrderMeterTrigger savedWorkOrderMeterTrigger = workOrderMeterTriggerRepository.findById(id).get();
            WorkOrderMeterTrigger updatedWorkOrderMeterTrigger =
                    workOrderMeterTriggerRepository.save(workOrderMeterTriggerMapper.updateWorkOrderMeterTrigger(savedWorkOrderMeterTrigger, workOrderMeterTrigger));
            return updatedWorkOrderMeterTrigger;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<WorkOrderMeterTrigger> getAll() {
        return workOrderMeterTriggerRepository.findAll();
    }

    public void delete(Long id) {
        workOrderMeterTriggerRepository.deleteById(id);
    }

    public Optional<WorkOrderMeterTrigger> findById(Long id) {
        return workOrderMeterTriggerRepository.findById(id);
    }

    public Collection<WorkOrderMeterTrigger> findByMeter(Long id) {
        return workOrderMeterTriggerRepository.findByMeter_Id(id);
    }
}

