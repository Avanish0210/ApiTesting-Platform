package com.example.ApiTesting.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ExecutionComparisonResponse {

    private UUID firstExecutionId;

    private UUID secondExecutionId;

    private Integer firstStatus;

    private Integer secondStatus;

    private boolean statusChanged;

    private Long firstResponseTime;

    private Long secondResponseTime;

    private Long responseTimeDifference;

    private boolean bodyChanged;

    private List<String> addedHeaders;

    private List<String> removedHeaders;

    private List<String> modifiedHeaders;

}