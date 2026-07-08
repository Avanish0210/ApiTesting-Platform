package com.example.ApiTesting.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CollectionAnalyticsResponse {

    private UUID collectionId;

    private String collectionName;

    private Long executions;

    private Double successRate;

    private Double averageExecutionTime;

}
