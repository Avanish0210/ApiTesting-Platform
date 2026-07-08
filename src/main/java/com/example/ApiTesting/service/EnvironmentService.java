package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CreateEnvironmentRequest;
import com.example.ApiTesting.dto.EnvironmentResponse;
import com.example.ApiTesting.dto.UpdateEnvironmentRequest;

import java.util.List;
import java.util.UUID;

public interface EnvironmentService {

    EnvironmentResponse create(CreateEnvironmentRequest request);

    EnvironmentResponse update(
            UUID environmentId,
            UpdateEnvironmentRequest request);

    EnvironmentResponse findById(UUID environmentId);

    List<EnvironmentResponse> findAll();

    void delete(UUID environmentId);

    EnvironmentResponse activate(UUID environmentId);

    EnvironmentResponse getActiveEnvironment();
}
