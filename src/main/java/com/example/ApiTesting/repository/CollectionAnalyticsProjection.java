package com.example.ApiTesting.repository;

import java.util.UUID;

public interface CollectionAnalyticsProjection {

    UUID getCollectionId();

    String getCollectionName();

    Long getExecutions();

    Long getSuccessfulExecutions();

    Double getAverageExecutionTime();

}
