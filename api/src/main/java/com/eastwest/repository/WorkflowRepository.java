package com.eastwest.repository;

import com.eastwest.model.Workflow;
import com.eastwest.model.enums.workflow.WFMainCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    Collection<Workflow> findByCompany_Id(Long id);

    Collection<Workflow> findByMainConditionAndCompany_Id(WFMainCondition mainCondition, Long companyId);
}
