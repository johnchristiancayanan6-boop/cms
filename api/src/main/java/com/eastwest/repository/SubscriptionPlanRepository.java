package com.eastwest.repository;

import com.eastwest.model.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionPlanRepository extends JpaRepository<com.eastwest.model.SubscriptionPlan, Long> {
    boolean existsByCode(String code);

    Optional<SubscriptionPlan> findByCode(String code);
}
