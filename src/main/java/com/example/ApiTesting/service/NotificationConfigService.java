package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CreateNotificationConfigRequest;
import com.example.ApiTesting.dto.NotificationConfigResponse;
import com.example.ApiTesting.dto.UpdateNotificationConfigRequest;

import java.util.List;
import java.util.UUID;

public interface NotificationConfigService {

    NotificationConfigResponse create(CreateNotificationConfigRequest request);

    NotificationConfigResponse update(UUID id,
                                      UpdateNotificationConfigRequest request);

    NotificationConfigResponse findById(UUID id);

    List<NotificationConfigResponse> findAll();

    void delete(UUID id);

    NotificationConfigResponse enable(UUID id);

    NotificationConfigResponse disable(UUID id);

    void test(UUID id);
}
