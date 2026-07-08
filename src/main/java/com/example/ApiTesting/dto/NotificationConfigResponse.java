package com.example.ApiTesting.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationConfigResponse {

    private UUID id;

    private String name;

    private Boolean enabled;

    private String email;

    private String slackWebhook;

    private String teamsWebhook;

    private String discordWebhook;

    private String webhook;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
