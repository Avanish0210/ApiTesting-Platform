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
public class RecentExecutionResponse {

    private UUID runId;

    private UUID collectionId;

    private String collectionName;

    private RunStatus status;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Long totalExecutionTime;

    private Integer totalRequests;

    private Integer successfulRequests;

    private Integer failedRequests;

}
