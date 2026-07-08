package com.example.ApiTesting.repository;

import com.example.ApiTesting.entity.CollectionRun;
import com.example.ApiTesting.entity.RunStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CollectionRunRepository extends JpaRepository<CollectionRun, UUID> {
    List<CollectionRun> findByCollectionIdOrderByStartedAtDesc(UUID collectionId);
    long countByStatus(RunStatus status);
    List<CollectionRun> findTop10ByOrderByStartedAtDesc();

    @Query("""
    SELECT COALESCE(AVG(c.totalExecutionTime),0)
    FROM CollectionRun c
    """)
    Double averageExecutionTime();

    @Query("""
    SELECT
    DATE(c.startedAt) as date,
    
    COUNT(c) as totalExecutions,
    
    SUM(
    CASE
    WHEN c.status='COMPLETED'
    THEN 1
    ELSE 0
    END
    ) as successfulExecutions,
    
    SUM(
    CASE
    WHEN c.status='FAILED'
    THEN 1
    ELSE 0
    END
    ) as failedExecutions
    
    FROM CollectionRun c
    
    GROUP BY DATE(c.startedAt)
    
    ORDER BY DATE(c.startedAt)
    """)
    List<ExecutionTrendProjection> executionTrend();


    @Query("""
    SELECT
    c.collection.id as collectionId,
    c.collection.name as collectionName,
    COUNT(c) as executions,
    SUM(
    CASE
    WHEN c.status='COMPLETED'
    THEN 1
    ELSE 0
    END
    ) as successfulExecutions,
    AVG(c.totalExecutionTime) as averageExecutionTime
    FROM CollectionRun c
    GROUP BY c.collection.id,c.collection.name
    ORDER BY COUNT(c) DESC
    """)
    List<CollectionAnalyticsProjection> collectionAnalytics();
}
