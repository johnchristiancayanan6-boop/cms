package com.eastwest.repository;

import com.eastwest.model.TaskBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskBaseRepository extends JpaRepository<TaskBase, Long> {
}
