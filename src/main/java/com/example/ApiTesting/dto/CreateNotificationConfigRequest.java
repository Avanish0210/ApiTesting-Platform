package com.example.ApiTesting.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationConfigRequest {

    private String name;

    private String email;

    private String slackWebhook;

    private String teamsWebhook;

    private String discordWebhook;

    private String webhook;
}
