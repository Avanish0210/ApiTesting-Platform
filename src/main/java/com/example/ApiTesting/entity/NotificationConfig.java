package com.example.ApiTesting.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification_configs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private Boolean enabled;

    // Email
    private String email;

    // Slack
    @Column(columnDefinition = "TEXT")
    private String slackWebhook;

    // Microsoft Teams
    @Column(columnDefinition = "TEXT")
    private String teamsWebhook;

    // Discord
    @Column(columnDefinition = "TEXT")
    private String discordWebhook;

    // Generic Webhook
    @Column(columnDefinition = "TEXT")
    private String webhook;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
