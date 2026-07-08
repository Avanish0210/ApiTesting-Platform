package com.example.ApiTesting.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ExecutionStatisticsResponse {

    private long totalExecutions;

    private long successfulExecutions;

    private long failedExecutions;

    private double successRate;

    private long averageResponseTime;

    private long minimumResponseTime;

    private long maximumResponseTime;

    private LocalDateTime lastExecutionAt;

    private Map<Integer, Long> statusCodeDistribution;

}
