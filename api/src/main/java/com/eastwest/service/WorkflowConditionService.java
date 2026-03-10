package com.eastwest.service;

import com.eastwest.dto.WorkflowConditionPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.WorkflowConditionMapper;
import com.eastwest.model.WorkflowCondition;
import com.eastwest.repository.WorkflowConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkflowConditionService {
    private final WorkflowConditionRepository workflowConditionRepository;
    private final WorkflowConditionMapper workflowConditionMapper;

    public WorkflowCondition create(WorkflowCondition WorkflowCondition) {
        return workflowConditionRepository.save(WorkflowCondition);
    }

    public WorkflowCondition update(Long id, WorkflowConditionPatchDTO workflowConditionsPatchDTO) {
        if (workflowConditionRepository.existsById(id)) {
            WorkflowCondition savedWorkflowCondition = workflowConditionRepository.findById(id).get();
            return workflowConditionRepository.save(workflowConditionMapper.updateWorkflowCondition(savedWorkflowCondition, workflowConditionsPatchDTO));
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<WorkflowCondition> getAll() {
        return workflowConditionRepository.findAll();
    }

    public void delete(Long id) {
        workflowConditionRepository.deleteById(id);
    }

    public Optional<WorkflowCondition> findById(Long id) {
        return workflowConditionRepository.findById(id);
    }

    public Collection<WorkflowCondition> saveAll(Collection<WorkflowCondition> workflowConditions) {
        return workflowConditionRepository.saveAll(workflowConditions);
    }

}
