package com.eastwest.repository;

import com.eastwest.model.Asset;
import com.eastwest.model.UiConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UiConfigurationRepository extends JpaRepository<UiConfiguration, Long> {

    Optional<UiConfiguration> findByCompanySettings_Id(Long id);
}

