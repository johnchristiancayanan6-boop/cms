package com.eastwest.service;

import com.eastwest.dto.ChecklistPatchDTO;
import com.eastwest.dto.ChecklistPostDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.model.Checklist;
import com.eastwest.model.Company;
import com.eastwest.model.TaskBase;
import com.eastwest.dto.license.LicenseEntitlement;
import com.eastwest.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.eastwest.utils.Consts.usageBasedLicenseLimits;

@Service
@RequiredArgsConstructor
public class ChecklistService {
    private final CheckListRepository checklistRepository;
    private final CompanySettingsService companySettingsService;
    private final TaskBaseService taskBaseService;
    private final EntityManager em;
    private final LicenseService licenseService;

    @Transactional
    public Checklist createPost(ChecklistPostDTO checklistReq, Company company) {
        checkUsageBasedLimit(company);
        List<TaskBase> taskBases = checklistReq.getTaskBases().stream()
                .map(taskBaseDto -> taskBaseService.createFromTaskBaseDTO(taskBaseDto, company)).collect(Collectors.toList());
        Checklist checklist = Checklist.builder()
                .name(checklistReq.getName())
                .companySettings(company.getCompanySettings())
                .taskBases(taskBases)
                .category(checklistReq.getCategory())
                .description(checklistReq.getDescription())
                .build();
        Checklist savedChecklist = checklistRepository.saveAndFlush(checklist);
        em.refresh(savedChecklist);
        return savedChecklist;
    }

    @Transactional
    public Checklist update(Long id, ChecklistPatchDTO checklistReq, Company company) {
        if (checklistRepository.existsById(id)) {
            Checklist savedChecklist = checklistRepository.getById(id);
            savedChecklist.setCategory(checklistReq.getCategory());
            savedChecklist.setDescription(checklistReq.getDescription());
            savedChecklist.setName(checklistReq.getName());
            savedChecklist.getTaskBases().clear();
            List<TaskBase> taskBases = checklistReq.getTaskBases().stream()
                    .map(taskBaseDto -> taskBaseService.createFromTaskBaseDTO(taskBaseDto, company)).collect(Collectors.toList());
            savedChecklist.getTaskBases().addAll(taskBases);
            Checklist updatedChecklist = checklistRepository.saveAndFlush(savedChecklist);
            em.refresh(updatedChecklist);
            return updatedChecklist;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    private void checkUsageBasedLimit(Company company) {
        // LICENSE REMOVED: No longer checking UNLIMITED_CHECKLISTS entitlement
        // All checklist creation is now unlimited
    }

    public Collection<Checklist> getAll() {
        return checklistRepository.findAll();
    }

    public void delete(Long id) {
        checklistRepository.deleteById(id);
    }

    public Optional<Checklist> findById(Long id) {
        return checklistRepository.findById(id);
    }

    public Collection<Checklist> findByCompanySettings(Long id) {
        return checklistRepository.findByCompanySettings_Id(id);
    }

}

