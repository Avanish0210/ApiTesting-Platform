package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CreateScheduleRequest;
import com.example.ApiTesting.dto.ScheduleExecutionResponse;
import com.example.ApiTesting.dto.ScheduleResponse;
import com.example.ApiTesting.dto.UpdateScheduleRequest;

import java.util.List;
import java.util.UUID;

public interface ScheduleService {

    ScheduleResponse create(CreateScheduleRequest request);

    ScheduleResponse update(UUID id,
                            UpdateScheduleRequest request);

    ScheduleResponse findById(UUID id);

    List<ScheduleResponse> findAll();

    void delete(UUID id);

    ScheduleResponse enable(UUID id);

    ScheduleResponse disable(UUID id);

    List<ScheduleExecutionResponse> executionHistory(UUID scheduleId);
}
