package com.example.ApiTesting.dto;

import com.example.ApiTesting.entity.RunStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleExecutionResponse {

    private UUID id;

    private UUID scheduleId;

    private UUID collectionRunId;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private RunStatus status;

    private String message;
}
