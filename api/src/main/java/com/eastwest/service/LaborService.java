package com.eastwest.service;

import com.eastwest.dto.LaborPatchDTO;
import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.LaborMapper;
import com.eastwest.model.Labor;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.RoleType;
import com.eastwest.model.enums.TimeStatus;
import com.eastwest.repository.LaborRepository;
import com.eastwest.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LaborService {
    private final LaborRepository laborRepository;

    private final CompanyService companyService;
    private final TimeCategoryService timeCategoryService;
    private final UserService userService;
    private final WorkOrderService workOrderService;
    private final LaborMapper laborMapper;
    private final EntityManager em;
    private final LicenseService licenseService;

    @Transactional
    public Labor create(Labor labor) {
        // LICENSE REMOVED: No longer checking TIME_TRACKING entitlement
        Labor savedLabor = laborRepository.saveAndFlush(labor);
        em.refresh(savedLabor);
        return savedLabor;
    }

    @Transactional
    public Labor update(Long id, LaborPatchDTO labor) {
        if (laborRepository.existsById(id)) {
            Labor savedLabor = laborRepository.findById(id).get();
            Labor updatedLabor = laborRepository.saveAndFlush(laborMapper.updateLabor(savedLabor, labor));
            em.refresh(updatedLabor);
            return updatedLabor;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Labor save(Labor labor) {
        return laborRepository.save(labor);
    }

    public Collection<Labor> getAll() {
        return laborRepository.findAll();
    }

    public void delete(Long id) {
        laborRepository.deleteById(id);
    }

    public Optional<Labor> findById(Long id) {
        return laborRepository.findById(id);
    }

    public Collection<Labor> findByWorkOrder(Long id) {
        return laborRepository.findByWorkOrder_Id(id);
    }

    public Labor stop(Labor labor) {
        labor.setStatus(TimeStatus.STOPPED);
        labor.setDuration(labor.getDuration() + Helper.getDateDiff(labor.getStartedAt(), new Date(), TimeUnit.SECONDS));
        return save(labor);
    }
}

