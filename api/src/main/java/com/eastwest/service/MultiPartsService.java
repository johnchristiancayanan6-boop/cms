package com.eastwest.service;

import com.eastwest.dto.MultiPartsPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.MultiPartsMapper;
import com.eastwest.model.MultiParts;
import com.eastwest.model.OwnUser;
import com.eastwest.model.enums.RoleType;
import com.eastwest.repository.MultiPartsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MultiPartsService {
    private final MultiPartsRepository multiPartsRepository;
    private final CompanyService companyService;
    private final MultiPartsMapper multiPartsMapper;
    private final EntityManager em;
    private final PartService partService;

    @Transactional
    public MultiParts create(MultiParts multiParts) {
        MultiParts savedMultiParts = multiPartsRepository.saveAndFlush(multiParts);
        em.refresh(savedMultiParts);
        return savedMultiParts;
    }

    @Transactional
    public MultiParts update(Long id, MultiPartsPatchDTO multiPartsPatchDTO) {
        if (multiPartsRepository.existsById(id)) {
            MultiParts savedMultiParts = multiPartsRepository.findById(id).get();
            MultiParts updatedMultiParts = multiPartsRepository.saveAndFlush(multiPartsMapper.updateMultiParts(savedMultiParts, multiPartsPatchDTO));
            em.refresh(updatedMultiParts);
            return updatedMultiParts;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public Collection<MultiParts> getAll() {
        return multiPartsRepository.findAll();
    }

    public void delete(Long id) {
        multiPartsRepository.deleteById(id);
    }

    public Optional<MultiParts> findById(Long id) {
        return multiPartsRepository.findById(id);
    }

    public Collection<MultiParts> findByCompany(Long id) {
        return multiPartsRepository.findByCompany_Id(id);
    }

}

