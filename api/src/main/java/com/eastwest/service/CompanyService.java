package com.eastwest.service;

import com.eastwest.dto.CompanyPatchDTO;
import com.eastwest.exception.CustomException;
import com.eastwest.mapper.CompanyMapper;
import com.eastwest.model.Company;
import com.eastwest.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final EntityManager em;
    private final RoleService roleService;

    public Company create(Company company) {
        company.getCompanySettings().setRoleList(roleService.findDefaultRoles());
        return companyRepository.save(company);
    }

    public Company update(Company Company) {
        return companyRepository.save(Company);
    }

    public Collection<Company> getAll() {
        return companyRepository.findAll();
    }

    public void delete(Long id) {
        companyRepository.deleteById(id);
    }

    public Optional<Company> findById(Long id) {
        return companyRepository.findById(id);
    }

    @Transactional
    public Company update(Long id, CompanyPatchDTO company) {
        if (companyRepository.existsById(id)) {
            Company savedCompany = companyRepository.findById(id).get();
            Company updatedCompany = companyRepository.saveAndFlush(companyMapper.updateCompany(savedCompany, company));
            em.refresh(updatedCompany);
            return updatedCompany;
        } else throw new CustomException("Not found", HttpStatus.NOT_FOUND);
    }

    public boolean existsAtLeastOneWithMinWorkOrders() {//superAdmin and user's company
        return companyRepository.existsAtLeastOneWithMinWorkOrders();
    }

}

