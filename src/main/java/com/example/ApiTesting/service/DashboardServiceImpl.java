package com.example.ApiTesting.service;


import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.RunStatus;
import com.example.ApiTesting.repository.CollectionRepository;
import com.example.ApiTesting.repository.CollectionRunRepository;
import com.example.ApiTesting.repository.ExecutionHistoryRepository;
import com.example.ApiTesting.repository.SavedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
    private final CollectionRepository collectionRepository;

    private final SavedRequestRepository savedRequestRepository;

    private final CollectionRunRepository collectionRunRepository;
    private final ExecutionHistoryRepository executionHistoryRepository;

    @Override
    public DashboardOverviewResponse overview() {

        long totalCollections =
                collectionRepository.count();

        long totalRequests =
                savedRequestRepository.count();

        long totalExecutions =
                collectionRunRepository.count();

        long successful =
                collectionRunRepository.countByStatus(
                        RunStatus.COMPLETED);

        long failed =
                collectionRunRepository.countByStatus(
                        RunStatus.FAILED);

        double successRate =
                totalExecutions == 0
                        ? 0
                        : (successful * 100.0) / totalExecutions;

        double averageResponseTime =
                collectionRunRepository.averageExecutionTime();

        return DashboardOverviewResponse.builder()
                .totalCollections(totalCollections)
                .totalRequests(totalRequests)
                .totalExecutions(totalExecutions)
                .successfulExecutions(successful)
                .failedExecutions(failed)
                .successRate(successRate)
                .averageResponseTime(averageResponseTime)
                .build();

    }

    @Override
    public List<RecentExecutionResponse> recentExecutions() {

        return collectionRunRepository
                .findTop10ByOrderByStartedAtDesc()
                .stream()
                .map(run ->
                        RecentExecutionResponse.builder()
                                .runId(run.getId())
                                .collectionId(run.getCollection().getId())
                                .collectionName(run.getCollection().getName())
                                .status(run.getStatus())
                                .startedAt(run.getStartedAt())
                                .finishedAt(run.getFinishedAt())
                                .totalExecutionTime(run.getTotalExecutionTime())
                                .totalRequests(run.getTotalRequests())
                                .successfulRequests(run.getSuccessfulRequests())
                                .failedRequests(run.getFailedRequests())
                                .build()
                )
                .toList();

    }

    @Override
    public List<ExecutionTrendResponse> executionTrend() {

        return collectionRunRepository
                .executionTrend()
                .stream()
                .map(t ->
                        ExecutionTrendResponse.builder()
                                .date(t.getDate())
                                .totalExecutions(
                                        t.getTotalExecutions())
                                .successfulExecutions(
                                        t.getSuccessfulExecutions())
                                .failedExecutions(
                                        t.getFailedExecutions())
                                .build()
                )
                .toList();

    }
    @Override
    public List<SlowApiResponse> slowApis() {

        return executionHistoryRepository
                .findTopSlowApis(PageRequest.of(0,10))
                .stream()
                .map(api ->
                        SlowApiResponse.builder()
                                .requestId(api.getRequestId())
                                .requestName(api.getRequestName())
                                .averageResponseTime(api.getAverageResponseTime())
                                .executions(api.getExecutions())
                                .build())
                .toList();

    }

    @Override
    public List<FailedApiResponse> failedApis() {

        return executionHistoryRepository
                .findTopFailedApis(PageRequest.of(0, 10))
                .stream()
                .map(api -> {

                    double rate = api.getExecutions() == 0
                            ? 0
                            : (api.getFailures() * 100.0) / api.getExecutions();

                    return FailedApiResponse.builder()
                            .requestId(api.getRequestId())
                            .requestName(api.getRequestName())
                            .failures(api.getFailures())
                            .executions(api.getExecutions())
                            .failureRate(rate)
                            .build();

                })
                .toList();

    }

    @Override
    public List<StatusCodeAnalyticsResponse> statusCodes() {

        return executionHistoryRepository
                .statusCodes()
                .stream()
                .map(status ->
                        StatusCodeAnalyticsResponse.builder()
                                .statusCode(status.getStatusCode())
                                .count(status.getCount())
                                .build())
                .toList();

    }

    @Override
    public List<CollectionAnalyticsResponse> collectionAnalytics() {

        return collectionRunRepository
                .collectionAnalytics()
                .stream()
                .map(c -> {

                    double successRate =
                            c.getExecutions() == 0
                                    ? 0
                                    : c.getSuccessfulExecutions() * 100.0
                                    / c.getExecutions();

                    return CollectionAnalyticsResponse.builder()
                            .collectionId(c.getCollectionId())
                            .collectionName(c.getCollectionName())
                            .executions(c.getExecutions())
                            .successRate(successRate)
                            .averageExecutionTime(c.getAverageExecutionTime())
                            .build();

                })
                .toList();
    }

}
