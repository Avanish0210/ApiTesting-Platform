package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnvironmentRepository
        extends JpaRepository<Environment, UUID> {

    Optional<Environment> findByActiveTrue();

    boolean existsByName(String name);

}
