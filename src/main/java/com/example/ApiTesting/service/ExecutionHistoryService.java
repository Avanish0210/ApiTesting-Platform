package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.SavedRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ExecutionHistoryService {

    void save(SavedRequest savedRequest, ApiTestResponse response);
    ExecutionHistoryResponse findById(UUID id);
    Page<ExecutionHistoryResponse> findByRequest(UUID requestId, ExecutionHistoryFilter filter, Pageable pageable);
    void delete(UUID historyId);
    void deleteAll(UUID requestId);
    ExecutionStatisticsResponse getStatistics(UUID requestId);
    ExecutionComparisonResponse compare(UUID firstHistoryId, UUID secondHistoryId);
    String exportJson(UUID requestId);
    String exportCsv(UUID requestId);

}
