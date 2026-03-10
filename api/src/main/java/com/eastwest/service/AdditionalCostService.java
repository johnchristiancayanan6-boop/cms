package com.eastwest.service;

import com.eastwest.dto.AdditionalCostPatchDTO;
import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.AdditionalCostMapper;
import com.eastwest.model.AdditionalCost;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.RoleType;
import com.eastwest.repository.AdditionalCostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdditionalCostService {

    private final AdditionalCostRepository additionalCostRepository;
    private final EntityManager em;


    private final AdditionalCostMapper additionalCostMapper;
    private final LicenseService licenseService;

    @Transactional
    public AdditionalCost create(AdditionalCost additionalCost) {
        // LICENSE REMOVED: No longer checking COST_TRACKING entitlement
        AdditionalCost savedAdditionalCost = additionalCostRepository.saveAndFlush(additionalCost);
        em.refresh(savedAdditionalCost);
        return savedAdditionalCost;
    }

    @Transactional
    public AdditionalCost update(Long id, AdditionalCostPatchDTO additionalCost) {
        if (additionalCostRepository.existsById(id)) {
            AdditionalCost savedAdditionalCost = additionalCostRepository.findById(id).get();
            AdditionalCost updatedAdditionalCost =
                    additionalCostRepository.saveAndFlush(additionalCostMapper.updateAdditionalCost(savedAdditionalCost, additionalCost));
            em.refresh(updatedAdditionalCost);
            return updatedAdditionalCost;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<AdditionalCost> getAll() {
        return additionalCostRepository.findAll();
    }

    public void delete(Long id) {
        additionalCostRepository.deleteById(id);
    }

    public Optional<AdditionalCost> findById(Long id) {
        return additionalCostRepository.findById(id);
    }

    public Collection<AdditionalCost> findByWorkOrder(Long id) {
        return additionalCostRepository.findByWorkOrder_Id(id);
    }
}

