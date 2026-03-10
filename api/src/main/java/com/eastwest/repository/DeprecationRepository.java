package com.eastwest.repository;

import com.eastwest.model.Deprecation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeprecationRepository extends JpaRepository<Deprecation, Long> {
}
