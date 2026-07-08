package com.example.ApiTesting.repository;

import java.util.UUID;

public interface SlowApiProjection {

    UUID getRequestId();

    String getRequestName();

    Double getAverageResponseTime();

    Long getExecutions();

}
