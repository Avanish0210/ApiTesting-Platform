package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.EnvironmentVariable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EnvironmentVariableRepository
        extends JpaRepository<EnvironmentVariable, UUID> {

    List<EnvironmentVariable> findByEnvironmentId(UUID environmentId);

}
