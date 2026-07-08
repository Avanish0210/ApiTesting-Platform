package com.example.ApiTesting.mapper;


import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.ExecutionHistory;
import com.example.ApiTesting.entity.SavedRequest;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
public class ExecutionHistoryMapper {

    public ExecutionHistorySummary toSummary(
            ExecutionHistory history){

        return ExecutionHistorySummary.builder()
                .id(history.getId())
                .statusCode(history.getStatusCode())
                .responseTime(history.getResponseTime())
                .success(history.getSuccess())
                .executedAt(history.getExecutedAt())
                .build();

    }

    public ExecutionHistoryResponse toResponse(
            ExecutionHistory history){

        return ExecutionHistoryResponse.builder()
                .id(history.getId())
                .statusCode(history.getStatusCode())
                .responseTime(history.getResponseTime())
                .responseHeaders(history.getResponseHeaders())
                .responseBody(history.getResponseBody())
                .success(history.getSuccess())
                .passedAssertions(history.getPassedAssertions())
                .failedAssertions(history.getFailedAssertions())
                .assertionResults(
                        JsonUtil.fromJson(
                                history.getAssertionResults(),
                                AssertionResult[].class
                        ) == null
                                ? null
                                : java.util.Arrays.asList(
                                JsonUtil.fromJson(
                                        history.getAssertionResults(),
                                        AssertionResult[].class)))
                .executedAt(history.getExecutedAt())
                .build();

    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    public ExecutionHistory toEntity(
            SavedRequest savedRequest,
            ApiTestResponse response) {

        ExecutionHistory history = new ExecutionHistory();

        history.setSavedRequest(savedRequest);

        history.setStatusCode(response.getStatus());

        history.setResponseTime(response.getResponseTime());

        history.setSuccess(response.getFailedAssertions() == 0);

        history.setPassedAssertions(response.getPassedAssertions());

        history.setFailedAssertions(response.getFailedAssertions());

        history.setResponseBody(response.getResponseBody());

        try {

            history.setResponseHeaders(
                    objectMapper.writeValueAsString(
                            response.getHeaders()));

            history.setAssertionResults(
                    objectMapper.writeValueAsString(
                            response.getAssertionResults()));

        } catch (Exception e) {

            history.setResponseHeaders("[]");
            history.setAssertionResults("[]");

        }

        return history;
    }

}