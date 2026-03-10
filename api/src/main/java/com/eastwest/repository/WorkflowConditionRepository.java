package com.eastwest.repository;

import com.eastwest.model.WorkflowCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface WorkflowConditionRepository extends JpaRepository<WorkflowCondition, Long> {
    Collection<WorkflowCondition> findByCompany_Id(Long id);
}
