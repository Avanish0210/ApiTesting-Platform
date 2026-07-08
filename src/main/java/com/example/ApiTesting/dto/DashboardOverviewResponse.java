package com.example.ApiTesting.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardOverviewResponse {

    private long totalCollections;

    private long totalRequests;

    private long totalExecutions;

    private long successfulExecutions;

    private long failedExecutions;

    private double successRate;

    private double averageResponseTime;

}
