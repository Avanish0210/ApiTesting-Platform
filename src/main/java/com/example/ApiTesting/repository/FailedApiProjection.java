package com.example.ApiTesting.repository;

import java.util.UUID;

public interface FailedApiProjection {

    UUID getRequestId();

    String getRequestName();

    Long getFailures();

    Long getExecutions();

}
