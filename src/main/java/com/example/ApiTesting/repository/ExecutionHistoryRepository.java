package com.example.ApiTesting.repository;


import com.example.ApiTesting.entity.ExecutionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExecutionHistoryRepository extends JpaRepository<ExecutionHistory, UUID>, JpaSpecificationExecutor<ExecutionHistory> {

    void deleteBySavedRequestId(UUID requestId);

    List<ExecutionHistory> findBySavedRequestId(UUID requestId);

    long countBySavedRequestId(UUID requestId);

    long countBySavedRequestIdAndSuccess(UUID requestId, Boolean success);

    void deleteByExecutedAtBefore(LocalDateTime date);

}

