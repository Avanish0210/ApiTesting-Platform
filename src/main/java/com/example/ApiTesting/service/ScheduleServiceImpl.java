package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CreateScheduleRequest;
import com.example.ApiTesting.dto.ScheduleExecutionResponse;
import com.example.ApiTesting.dto.ScheduleResponse;
import com.example.ApiTesting.dto.UpdateScheduleRequest;
import com.example.ApiTesting.entity.Collection;
import com.example.ApiTesting.entity.Schedule;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.ScheduleMapper;
import com.example.ApiTesting.repository.CollectionRepository;
import com.example.ApiTesting.repository.ScheduleExecutionRepository;
import com.example.ApiTesting.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CollectionRepository collectionRepository;
    private final ScheduleExecutionRepository executionRepository;
    private final ScheduleMapper mapper;

    @Override
    @Transactional
    public ScheduleResponse enable(UUID id) {

        Schedule schedule =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found"));

        schedule.setEnabled(true);

        scheduleRepository.save(schedule);

        return mapper.toResponse(schedule);
    }

    @Override
    @Transactional
    public ScheduleResponse disable(UUID id) {

        Schedule schedule =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found"));

        schedule.setEnabled(false);

        scheduleRepository.save(schedule);

        return mapper.toResponse(schedule);
    }

    @Override
    public List<ScheduleExecutionResponse> executionHistory(
            UUID scheduleId) {

        return executionRepository
                .findByScheduleIdOrderByStartedAtDesc(scheduleId)
                .stream()
                .map(mapper::toExecutionResponse)
                .toList();
    }

    @Override
    @Transactional
    public ScheduleResponse create(CreateScheduleRequest request) {

        Collection collection =
                collectionRepository.findById(request.getCollectionId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Collection not found"));

        Schedule schedule =
                mapper.toEntity(request, collection);

        scheduleRepository.save(schedule);

        return mapper.toResponse(schedule);
    }

    @Override
    public ScheduleResponse findById(UUID id) {

        Schedule schedule =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found"));

        return mapper.toResponse(schedule);
    }

    @Override
    public List<ScheduleResponse> findAll() {

        return scheduleRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
    @Override
    @Transactional
    public ScheduleResponse update(UUID id,
                                   UpdateScheduleRequest request) {

        Schedule schedule =
                scheduleRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Schedule not found"));

        mapper.updateEntity(request, schedule);

        scheduleRepository.save(schedule);

        return mapper.toResponse(schedule);
    }

    @Override
    @Transactional
    public void delete(UUID id) {

        if (!scheduleRepository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Schedule not found");

        }

        scheduleRepository.deleteById(id);
    }
}
