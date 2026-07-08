package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.NotificationConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationConfigRepository
        extends JpaRepository<NotificationConfig, UUID> {

    List<NotificationConfig> findByEnabledTrue();


}
