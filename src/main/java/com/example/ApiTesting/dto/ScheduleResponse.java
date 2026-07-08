package com.example.ApiTesting.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {

    private UUID id;

    private String name;

    private UUID collectionId;

    private String collectionName;

    private String cronExpression;

    private Boolean enabled;

    private LocalDateTime lastRun;

    private LocalDateTime nextRun;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
