package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CreateNotificationConfigRequest;
import com.example.ApiTesting.dto.NotificationConfigResponse;
import com.example.ApiTesting.dto.UpdateNotificationConfigRequest;
import com.example.ApiTesting.entity.NotificationConfig;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.NotificationConfigMapper;
import com.example.ApiTesting.repository.NotificationConfigRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationConfigServiceImpl implements NotificationConfigService {

    private final NotificationConfigRepository repository;
    private final NotificationConfigMapper mapper;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public NotificationConfigResponse create(
            CreateNotificationConfigRequest request) {

        NotificationConfig config =
                mapper.toEntity(request);

        repository.save(config);

        return mapper.toResponse(config);
    }

    @Override
    public NotificationConfigResponse findById(UUID id) {

        NotificationConfig config =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification configuration not found"));

        return mapper.toResponse(config);
    }

    @Override
    public List<NotificationConfigResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public NotificationConfigResponse update(
            UUID id,
            UpdateNotificationConfigRequest request) {

        NotificationConfig config =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification configuration not found"));

        mapper.update(request, config);

        repository.save(config);

        return mapper.toResponse(config);
    }

    @Override
    @Transactional
    public void delete(UUID id) {

        if (!repository.existsById(id)) {

            throw new ResourceNotFoundException(
                    "Notification configuration not found");

        }

        repository.deleteById(id);
    }

    @Override
    @Transactional
    public NotificationConfigResponse enable(UUID id) {

        NotificationConfig config =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification configuration not found"));

        config.setEnabled(true);

        repository.save(config);

        return mapper.toResponse(config);
    }

    @Override
    @Transactional
    public NotificationConfigResponse disable(UUID id) {

        NotificationConfig config =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification configuration not found"));

        config.setEnabled(false);

        repository.save(config);

        return mapper.toResponse(config);
    }

    @Override
    public void test(UUID id) {

        NotificationConfig config =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Notification configuration not found"));

        notificationService.sendTestNotification(config);

    }
}
