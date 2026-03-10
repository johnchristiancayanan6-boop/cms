package com.eastwest.job;

import com.eastwest.model.Company;
import com.eastwest.repository.CompanyRepository;
import com.eastwest.repository.UserRepository;
import com.eastwest.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteDemoCompaniesJob implements Job {

    private final CompanyRepository companyRepository;

    @Value("${cloud-version}")
    private boolean cloudVersion;

    @Override
    @Transactional
    public void execute(JobExecutionContext context) {
        if (!cloudVersion) {
            return;
        }
        log.info("Deleting demo companies");
        companyRepository.deleteAllByDemoTrue();
        log.info("Deleted demo companies");
    }
}
