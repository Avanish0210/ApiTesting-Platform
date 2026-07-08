package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CreateEnvironmentRequest;
import com.example.ApiTesting.dto.EnvironmentResponse;
import com.example.ApiTesting.dto.UpdateEnvironmentRequest;
import com.example.ApiTesting.entity.Environment;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.EnvironmentMapper;
import com.example.ApiTesting.repository.EnvironmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EnvironmentServiceImpl
        implements EnvironmentService {

    private final EnvironmentRepository repository;
    private final EnvironmentMapper mapper;

    @Override
    public EnvironmentResponse create(CreateEnvironmentRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new IllegalArgumentException(
                    "Environment name already exists");
        }

        Environment environment = mapper.toEntity(request);

        repository.save(environment);

        return mapper.toResponse(environment);
    }

    @Override
    public EnvironmentResponse update(
            UUID environmentId,
            UpdateEnvironmentRequest request) {

        Environment environment =
                repository.findById(environmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Environment not found"));

        mapper.updateEntity(request, environment);

        repository.save(environment);

        return mapper.toResponse(environment);
    }

    @Override
    public EnvironmentResponse findById(UUID environmentId) {

        Environment environment =
                repository.findById(environmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Environment not found"));

        return mapper.toResponse(environment);
    }

    @Override
    public List<EnvironmentResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID environmentId) {

        if (!repository.existsById(environmentId)) {
            throw new ResourceNotFoundException(
                    "Environment not found");
        }

        repository.deleteById(environmentId);
    }

    @Override
    public EnvironmentResponse activate(UUID environmentId) {

        Environment environment =
                repository.findById(environmentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Environment not found"));

        repository.findByActiveTrue()
                .ifPresent(active -> {

                    active.setActive(false);
                    repository.save(active);

                });

        environment.setActive(true);

        repository.save(environment);

        return mapper.toResponse(environment);
    }

    @Override
    public EnvironmentResponse getActiveEnvironment() {

        Environment environment =
                repository.findByActiveTrue()
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "No active environment found"));

        return mapper.toResponse(environment);
    }

}
