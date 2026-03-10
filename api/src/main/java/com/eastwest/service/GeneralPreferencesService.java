package com.eastwest.service;

import com.eastwest.dto.GeneralPreferencesPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.GeneralPreferencesMapper;
import com.eastwest.model.GeneralPreferences;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.RoleType;
import com.eastwest.repository.GeneralPreferencesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeneralPreferencesService {
    private final GeneralPreferencesRepository generalPreferencesRepository;
    private final GeneralPreferencesMapper generalPreferencesMapper;

    public GeneralPreferences create(GeneralPreferences GeneralPreferences) {
        return generalPreferencesRepository.save(GeneralPreferences);
    }

    public GeneralPreferences update(Long id, GeneralPreferencesPatchDTO generalPreferencesPatchDTO) {
        if (generalPreferencesRepository.existsById(id)) {
            GeneralPreferences savedGeneralPreferences = generalPreferencesRepository.findById(id).get();
            return generalPreferencesRepository.save(generalPreferencesMapper.updateGeneralPreferences(savedGeneralPreferences, generalPreferencesPatchDTO));
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<GeneralPreferences> getAll() {
        return generalPreferencesRepository.findAll();
    }

    public void delete(Long id) {
        generalPreferencesRepository.deleteById(id);
    }

    public Optional<GeneralPreferences> findById(Long id) {
        return generalPreferencesRepository.findById(id);
    }

    public Collection<GeneralPreferences> findByCompanySettings(Long id) {
        return generalPreferencesRepository.findByCompanySettings_Id(id);
    }
}
