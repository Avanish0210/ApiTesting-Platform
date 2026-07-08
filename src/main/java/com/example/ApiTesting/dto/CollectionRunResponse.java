package com.example.ApiTesting.dto;

import com.example.ApiTesting.entity.RunStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionRunResponse {

    private UUID id;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Integer totalRequests;

    private Integer successfulRequests;

    private Integer failedRequests;

    private Long totalExecutionTime;

    private RunStatus status;

}
