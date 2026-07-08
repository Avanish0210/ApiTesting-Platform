package com.example.ApiTesting.repository;

import java.time.LocalDate;

public interface ExecutionTrendProjection {

    LocalDate getDate();

    Long getTotalExecutions();

    Long getSuccessfulExecutions();

    Long getFailedExecutions();

}
