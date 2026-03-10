package com.eastwest.service;

import com.eastwest.dto.DeprecationPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.DeprecationMapper;
import com.eastwest.model.Deprecation;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.RoleType;
import com.eastwest.repository.DeprecationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeprecationService {
    private final DeprecationRepository deprecationRepository;
    private final CompanyService companyService;
    private final DeprecationMapper deprecationMapper;

    public Deprecation create(Deprecation Deprecation) {
        return deprecationRepository.save(Deprecation);
    }

    public Deprecation update(Long id, DeprecationPatchDTO deprecation) {
        if (deprecationRepository.existsById(id)) {
            Deprecation savedDeprecation = deprecationRepository.findById(id).get();
            return deprecationRepository.save(deprecationMapper.updateDeprecation(savedDeprecation, deprecation));
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<Deprecation> getAll() {
        return deprecationRepository.findAll();
    }

    public void delete(Long id) {
        deprecationRepository.deleteById(id);
    }

    public Optional<Deprecation> findById(Long id) {
        return deprecationRepository.findById(id);
    }

}
