package com.eastwest.service;

import com.eastwest.dto.FloorPlanPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.FloorPlanMapper;
import com.eastwest.model.FloorPlan;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.RoleType;
import com.eastwest.repository.FloorPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FloorPlanService {
    private final FloorPlanRepository floorPlanRepository;
    private final FileService fileService;
    private final LocationService locationService;
    private final FloorPlanMapper floorPlanMapper;
    private final EntityManager em;

    @Transactional
    public FloorPlan create(FloorPlan floorPlan) {
        FloorPlan savedFloorPlan = floorPlanRepository.saveAndFlush(floorPlan);
        em.refresh(savedFloorPlan);
        return savedFloorPlan;
    }

    @Transactional
    public FloorPlan update(Long id, FloorPlanPatchDTO floorPlan) {
        if (floorPlanRepository.existsById(id)) {
            FloorPlan savedFloorPlan = floorPlanRepository.findById(id).get();
            FloorPlan updatedFloorPlan = floorPlanRepository.saveAndFlush(floorPlanMapper.updateFloorPlan(savedFloorPlan, floorPlan));
            em.refresh(updatedFloorPlan);
            return updatedFloorPlan;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<FloorPlan> getAll() {
        return floorPlanRepository.findAll();
    }

    public void delete(Long id) {
        floorPlanRepository.deleteById(id);
    }

    public Optional<FloorPlan> findById(Long id) {
        return floorPlanRepository.findById(id);
    }


    public Collection<FloorPlan> findByLocation(Long id) {
        return floorPlanRepository.findByLocation_Id(id);
    }
}

