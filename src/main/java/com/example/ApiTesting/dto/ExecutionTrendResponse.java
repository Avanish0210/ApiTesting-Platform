package com.example.ApiTesting.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionTrendResponse {

    private LocalDate date;

    private Long totalExecutions;

    private Long successfulExecutions;

    private Long failedExecutions;

}
