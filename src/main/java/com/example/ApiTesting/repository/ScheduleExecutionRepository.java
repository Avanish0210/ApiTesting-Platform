package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.ScheduleExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ScheduleExecutionRepository
        extends JpaRepository<ScheduleExecution, UUID> {

    List<ScheduleExecution>
    findByScheduleIdOrderByStartedAtDesc(UUID id);

}
