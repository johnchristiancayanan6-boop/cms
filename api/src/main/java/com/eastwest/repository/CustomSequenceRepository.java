package com.eastwest.repository;

import com.eastwest.model.CustomSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomSequenceRepository extends JpaRepository<CustomSequence, Long> {
    Optional<CustomSequence> findByCompanyId(Long companyId);
}
