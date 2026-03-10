package com.eastwest.service;

import com.eastwest.dto.WorkflowActionPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.WorkflowActionMapper;
import com.eastwest.model.WorkflowAction;
import com.eastwest.repository.WorkflowActionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkflowActionService {
    private final WorkflowActionRepository workflowActionRepository;
    private final WorkflowActionMapper workflowActionMapper;

    public WorkflowAction create(WorkflowAction WorkflowAction) {
        return workflowActionRepository.save(WorkflowAction);
    }

    public WorkflowAction update(Long id, WorkflowActionPatchDTO workflowActionsPatchDTO) {
        if (workflowActionRepository.existsById(id)) {
            WorkflowAction savedWorkflowAction = workflowActionRepository.findById(id).get();
            return workflowActionRepository.save(workflowActionMapper.updateWorkflowAction(savedWorkflowAction, workflowActionsPatchDTO));
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<WorkflowAction> getAll() {
        return workflowActionRepository.findAll();
    }

    public void delete(Long id) {
        workflowActionRepository.deleteById(id);
    }

    public Optional<WorkflowAction> findById(Long id) {
        return workflowActionRepository.findById(id);
    }

}
