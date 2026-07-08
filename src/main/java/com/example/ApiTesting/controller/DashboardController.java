package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/overview")
    public DashboardOverviewResponse overview() {

        return dashboardService.overview();

    }

    @GetMapping("/recent")
    public List<RecentExecutionResponse> recentExecutions() {
        return dashboardService.recentExecutions();

    }

    @GetMapping("/trends")
    public List<ExecutionTrendResponse> executionTrend() {

        return dashboardService.executionTrend();

    }
    @GetMapping("/slow-apis")
    public List<SlowApiResponse> slowApis() {

        return dashboardService.slowApis();

    }
    @GetMapping("/failed-apis")
    public List<FailedApiResponse> failedApis() {

        return dashboardService.failedApis();

    }
    @GetMapping("/status-codes")
    public List<StatusCodeAnalyticsResponse> statusCodes() {

        return dashboardService.statusCodes();

    }
    @GetMapping("/collections")
    public List<CollectionAnalyticsResponse> collections() {

        return dashboardService.collectionAnalytics();

    }

}
