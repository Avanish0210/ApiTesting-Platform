package com.example.ApiTesting.repository;


import com.example.ApiTesting.entity.ExecutionHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExecutionHistoryRepository extends JpaRepository<ExecutionHistory, UUID>, JpaSpecificationExecutor<ExecutionHistory> {

    void deleteBySavedRequestId(UUID requestId);

    List<ExecutionHistory> findBySavedRequestId(UUID requestId);

    long countBySavedRequestId(UUID requestId);

    long countBySavedRequestIdAndSuccess(UUID requestId, Boolean success);

    void deleteByExecutedAtBefore(LocalDateTime date);

    @Query("""
    SELECT
    h.savedRequest.id as requestId,
    h.savedRequest.name as requestName,
    AVG(h.responseTime) as averageResponseTime,
    COUNT(h) as executions
    FROM ExecutionHistory h
    GROUP BY h.savedRequest.id,h.savedRequest.name
    ORDER BY AVG(h.responseTime) DESC
    """)
    List<SlowApiProjection> findTopSlowApis(Pageable pageable);


    @Query("""
    SELECT
    h.savedRequest.id as requestId,
    h.savedRequest.name as requestName,
    SUM(
    CASE
    WHEN h.success=false
    THEN 1
    ELSE 0
    END
    ) as failures,
    COUNT(h) as executions
    FROM ExecutionHistory h
    GROUP BY h.savedRequest.id,h.savedRequest.name
    ORDER BY failures DESC
    """)
    List<FailedApiProjection> findTopFailedApis(Pageable pageable);

    @Query("""
    SELECT
    h.statusCode as statusCode,
    COUNT(h) as count
    FROM ExecutionHistory h
    GROUP BY h.statusCode
    ORDER BY h.statusCode
    """)
    List<StatusCodeProjection> statusCodes();

}

