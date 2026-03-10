package com.eastwest.repository;

import com.eastwest.model.FloorPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FloorPlanRepository extends JpaRepository<FloorPlan, Long> {
    Collection<FloorPlan> findByLocation_Id(Long id);
}
