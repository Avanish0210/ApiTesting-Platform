package com.example.ApiTesting.mapper;

import com.example.ApiTesting.dto.CreateNotificationConfigRequest;
import com.example.ApiTesting.dto.NotificationConfigResponse;
import com.example.ApiTesting.dto.UpdateNotificationConfigRequest;
import com.example.ApiTesting.entity.NotificationConfig;
import org.springframework.stereotype.Component;


@Component
public class NotificationConfigMapper {

    public NotificationConfig toEntity(CreateNotificationConfigRequest dto) {

        return NotificationConfig.builder()
                .name(dto.getName())
                .enabled(true)
                .email(dto.getEmail())
                .slackWebhook(dto.getSlackWebhook())
                .teamsWebhook(dto.getTeamsWebhook())
                .discordWebhook(dto.getDiscordWebhook())
                .webhook(dto.getWebhook())
                .build();
    }

    public NotificationConfigResponse toResponse(NotificationConfig entity) {

        return NotificationConfigResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .enabled(entity.getEnabled())
                .email(entity.getEmail())
                .slackWebhook(entity.getSlackWebhook())
                .teamsWebhook(entity.getTeamsWebhook())
                .discordWebhook(entity.getDiscordWebhook())
                .webhook(entity.getWebhook())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    public void update(UpdateNotificationConfigRequest dto,
                       NotificationConfig entity) {

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }

        if (dto.getEnabled() != null) {
            entity.setEnabled(dto.getEnabled());
        }

        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }

        if (dto.getSlackWebhook() != null) {
            entity.setSlackWebhook(dto.getSlackWebhook());
        }

        if (dto.getTeamsWebhook() != null) {
            entity.setTeamsWebhook(dto.getTeamsWebhook());
        }

        if (dto.getDiscordWebhook() != null) {
            entity.setDiscordWebhook(dto.getDiscordWebhook());
        }

        if (dto.getWebhook() != null) {
            entity.setWebhook(dto.getWebhook());
        }
    }
}
