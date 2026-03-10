package com.eastwest.service;

import com.eastwest.dto.TaskBaseDTO;
import com.eastwest.dto.TaskBasePatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.TaskBaseMapper;
import com.eastwest.model.Company;
import com.eastwest.model.TaskBase;
import com.eastwest.model.TaskOption;
import com.eastwest.repository.TaskBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskBaseService {
    private final TaskBaseRepository taskBaseRepository;
    private final CompanyService companyService;
    private final TaskBaseMapper taskBaseMapper;
    private final TaskOptionService taskOptionService;
    private final UserService userService;
    private final MeterService meterService;
    private final AssetService assetService;
    private final EntityManager em;

    @Transactional
    public TaskBase create(TaskBase TaskBase) {
        TaskBase taskBase = taskBaseRepository.saveAndFlush(TaskBase);
        em.refresh(taskBase);
        return taskBase;
    }

    @Transactional
    public TaskBase createFromTaskBaseDTO(TaskBaseDTO taskBaseDTO, Company company) {
        TaskBase taskBase = TaskBase.builder()
                .label(taskBaseDTO.getLabel())
                .taskType(taskBaseDTO.getTaskType())
                .build();
        if (taskBaseDTO.getUser() != null) {
            userService.findById(taskBaseDTO.getUser().getId()).ifPresent(taskBase::setUser);
        }
        if (taskBaseDTO.getAsset() != null) {
            assetService.findById(taskBaseDTO.getAsset().getId()).ifPresent(taskBase::setAsset);
        }
        if (taskBaseDTO.getMeter() != null) {
            meterService.findById(taskBaseDTO.getMeter().getId()).ifPresent(taskBase::setMeter);
        }
        TaskBase savedTaskBase = create(taskBase);

        if (taskBaseDTO.getOptions() != null) {
            taskBaseDTO.getOptions().forEach(option -> {
                if (!option.trim().isEmpty()) {
                    TaskOption taskOption = new TaskOption(option, savedTaskBase);
                    TaskOption savedTaskOption = taskOptionService.create(taskOption);
                    savedTaskBase.getOptions().add(savedTaskOption);
                }
            });
        }
        return savedTaskBase;
    }

    public TaskBase update(Long id, TaskBasePatchDTO taskBase) {
        if (taskBaseRepository.existsById(id)) {
            TaskBase savedTaskBase = taskBaseRepository.findById(id).get();
            return taskBaseRepository.save(taskBaseMapper.updateTaskBase(savedTaskBase, taskBase));
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<TaskBase> getAll() {
        return taskBaseRepository.findAll();
    }

    public void delete(Long id) {
        taskBaseRepository.deleteById(id);
    }

    public Optional<TaskBase> findById(Long id) {
        return taskBaseRepository.findById(id);
    }

}

