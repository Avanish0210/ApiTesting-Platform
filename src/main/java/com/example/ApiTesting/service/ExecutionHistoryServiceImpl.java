package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.ExecutionHistory;
import com.example.ApiTesting.entity.SavedRequest;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.ExecutionHistoryMapper;
import com.example.ApiTesting.repository.ExecutionHistoryRepository;
import com.example.ApiTesting.specification.ExecutionHistorySpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExecutionHistoryServiceImpl implements ExecutionHistoryService {

    private final ExecutionHistoryRepository repository;
    private final ExecutionHistoryMapper mapper;

    @Override
    public void save(SavedRequest savedRequest,
                     ApiTestResponse response) {

        ExecutionHistory history =
                mapper.toEntity(savedRequest, response);

        repository.save(history);
    }

    @Override
    public ExecutionHistoryResponse findById(UUID id){

        ExecutionHistory history =
                repository.findById(id)
                        .orElseThrow(()->
                                new ResourceNotFoundException(
                                        "Execution history not found"));

        return mapper.toResponse(history);

    }

    @Override
    @Transactional
    public Page<ExecutionHistoryResponse> findByRequest(
            UUID requestId,
            ExecutionHistoryFilter filter,
            Pageable pageable
            ) {

        Specification<ExecutionHistory> specification =
                Specification.where(
                                ExecutionHistorySpecification.hasRequestId(requestId))
                        .and(
                                ExecutionHistorySpecification.hasStatus(filter.getStatusCode()))
                        .and(
                                ExecutionHistorySpecification.hasSuccess(filter.getSuccess()))
                        .and(
                                ExecutionHistorySpecification.minResponseTime(filter.getMinResponseTime()))
                        .and(
                                ExecutionHistorySpecification.maxResponseTime(filter.getMaxResponseTime()))
                        .and(
                                ExecutionHistorySpecification.fromDate(filter.getFromDate()))
                        .and(
                                ExecutionHistorySpecification.toDate(filter.getToDate()));

        return repository.findAll(specification, pageable)
                .map(mapper::toResponse);
    }


    @Override
    public void delete(UUID historyId){

        if(!repository.existsById(historyId))
            throw new ResourceNotFoundException(
                    "History not found");

        repository.deleteById(historyId);

    }

    @Override
    public void deleteAll(UUID requestId){

        repository.deleteBySavedRequestId(requestId);

    }

    @Override
    @Transactional
    public ExecutionStatisticsResponse getStatistics(UUID requestId) {

        List<ExecutionHistory> histories = repository.findBySavedRequestId(requestId);

        if (histories.isEmpty()) {
            return ExecutionStatisticsResponse.builder()
                    .totalExecutions(0)
                    .successfulExecutions(0)
                    .failedExecutions(0)
                    .successRate(0)
                    .averageResponseTime(0)
                    .minimumResponseTime(0)
                    .maximumResponseTime(0)
                    .lastExecutionAt(null)
                    .statusCodeDistribution(Map.of())
                    .build();
        }

        long total = histories.size();

        long successful = histories.stream()
                .filter(ExecutionHistory::getSuccess)
                .count();

        long failed = total - successful;

        double successRate = (successful * 100.0) / total;

        long averageResponseTime = (long) histories.stream()
                .mapToLong(ExecutionHistory::getResponseTime)
                .average()
                .orElse(0);

        long minimumResponseTime = histories.stream()
                .mapToLong(ExecutionHistory::getResponseTime)
                .min()
                .orElse(0);

        long maximumResponseTime = histories.stream()
                .mapToLong(ExecutionHistory::getResponseTime)
                .max()
                .orElse(0);

        LocalDateTime lastExecution = histories.stream()
                .map(ExecutionHistory::getExecutedAt)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        Map<Integer, Long> statusDistribution =
                histories.stream()
                        .collect(Collectors.groupingBy(
                                ExecutionHistory::getStatusCode,
                                Collectors.counting()
                        ));

        return ExecutionStatisticsResponse.builder()
                .totalExecutions(total)
                .successfulExecutions(successful)
                .failedExecutions(failed)
                .successRate(successRate)
                .averageResponseTime(averageResponseTime)
                .minimumResponseTime(minimumResponseTime)
                .maximumResponseTime(maximumResponseTime)
                .lastExecutionAt(lastExecution)
                .statusCodeDistribution(statusDistribution)
                .build();
    }
    private Map<String, String> toHeaderMap(String json) {

        if (json == null || json.isBlank()) {
            return Collections.emptyMap();
        }

        List<HeaderDto> headers =
                JsonUtil.fromJsonList(json, HeaderDto.class);

        return headers.stream()
                .collect(Collectors.toMap(
                        HeaderDto::getKey,
                        HeaderDto::getValue,
                        (first, second) -> second
                ));
    }

    @Override
    public ExecutionComparisonResponse compare(UUID firstHistoryId, UUID secondHistoryId) {
        ExecutionHistory first =
                repository.findById(firstHistoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "History not found"));

        ExecutionHistory second =
                repository.findById(secondHistoryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "History not found"));

        if (!first.getSavedRequest().getId()
                .equals(second.getSavedRequest().getId())) {

            throw new IllegalArgumentException(
                    "Executions belong to different requests");

        }

        boolean statusChanged =
                !Objects.equals(
                        first.getStatusCode(),
                        second.getStatusCode());

        long difference =
                Math.abs(
                        first.getResponseTime()
                                - second.getResponseTime());

        boolean bodyChanged =
                !Objects.equals(
                        first.getResponseBody(),
                        second.getResponseBody());

        Map<String, String> firstHeaders =
                toHeaderMap(first.getResponseHeaders());

        Map<String, String> secondHeaders =
                toHeaderMap(second.getResponseHeaders());

        List<String> added = secondHeaders.keySet()
                .stream()
                .filter(key -> !firstHeaders.containsKey(key))
                .toList();
        List<String> removed = firstHeaders.keySet()
                .stream()
                .filter(key -> !secondHeaders.containsKey(key))
                .toList();
        List<String> modified = firstHeaders.keySet()
                .stream()
                .filter(secondHeaders::containsKey)
                .filter(key ->
                        !Objects.equals(
                                firstHeaders.get(key),
                                secondHeaders.get(key)))
                .toList();

        return ExecutionComparisonResponse.builder()

                .firstExecutionId(first.getId())
                .secondExecutionId(second.getId())

                .firstStatus(first.getStatusCode())
                .secondStatus(second.getStatusCode())

                .statusChanged(statusChanged)

                .firstResponseTime(first.getResponseTime())
                .secondResponseTime(second.getResponseTime())

                .responseTimeDifference(difference)

                .bodyChanged(bodyChanged)

                .addedHeaders(added)
                .removedHeaders(removed)
                .modifiedHeaders(modified)

                .build();
    }



    @Override
    @Transactional
    public String exportJson(UUID requestId) {

        List<ExecutionHistory> histories =
                repository.findBySavedRequestId(requestId);

        List<ExecutionHistoryResponse> response =
                histories.stream()
                        .map(mapper::toResponse)
                        .toList();

        return JsonUtil.toJson(response);
    }
    @Override
    @Transactional
    public String exportCsv(UUID requestId) {

        List<ExecutionHistory> histories =
                repository.findBySavedRequestId(requestId);

        StringBuilder csv = new StringBuilder();

        csv.append(
                "Executed At,Status,Success,Response Time,Passed Assertions,Failed Assertions\n");

        for (ExecutionHistory history : histories) {

            csv.append(history.getExecutedAt()).append(",");

            csv.append(history.getStatusCode()).append(",");

            csv.append(history.getSuccess()).append(",");

            csv.append(history.getResponseTime()).append(",");

            csv.append(history.getPassedAssertions()).append(",");

            csv.append(history.getFailedAssertions()).append("\n");

        }

        return csv.toString();

    }


}
