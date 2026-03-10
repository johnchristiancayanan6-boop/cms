package com.eastwest.service;

import com.eastwest.dto.FieldConfigurationPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.FieldConfigurationMapper;
import com.eastwest.model.FieldConfiguration;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.RoleType;
import com.eastwest.repository.FieldConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FieldConfigurationService {
    private final FieldConfigurationRepository fieldConfigurationRepository;
    private final FieldConfigurationMapper fieldConfigurationMapper;

    public FieldConfiguration create(FieldConfiguration FieldConfiguration) {
        return fieldConfigurationRepository.save(FieldConfiguration);
    }

    public FieldConfiguration update(Long id, FieldConfigurationPatchDTO fieldConfiguration) {
        if (fieldConfigurationRepository.existsById(id)) {
            FieldConfiguration savedFieldConfiguration = fieldConfigurationRepository.findById(id).get();
            return fieldConfigurationRepository.save(fieldConfigurationMapper.updateFieldConfiguration(savedFieldConfiguration, fieldConfiguration));
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<FieldConfiguration> getAll() {
        return fieldConfigurationRepository.findAll();
    }

    public void delete(Long id) {
        fieldConfigurationRepository.deleteById(id);
    }

    public Optional<FieldConfiguration> findById(Long id) {
        return fieldConfigurationRepository.findById(id);
    }
}
