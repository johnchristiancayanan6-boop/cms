package com.eastwest.repository;

import com.eastwest.model.WorkflowAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface WorkflowActionRepository extends JpaRepository<WorkflowAction, Long> {
    Collection<WorkflowAction> findByCompany_Id(Long id);
}
