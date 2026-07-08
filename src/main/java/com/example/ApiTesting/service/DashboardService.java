package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.*;

import java.util.List;

public interface DashboardService {
    DashboardOverviewResponse overview();
    List<RecentExecutionResponse> recentExecutions();
    List<ExecutionTrendResponse> executionTrend();
    List<SlowApiResponse> slowApis();
    List<FailedApiResponse> failedApis();
    List<StatusCodeAnalyticsResponse> statusCodes();
    List<CollectionAnalyticsResponse> collectionAnalytics();
}
