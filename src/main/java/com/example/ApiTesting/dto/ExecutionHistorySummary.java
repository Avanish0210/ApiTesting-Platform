package com.example.ApiTesting.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionHistorySummary {

    private UUID id;

    private Integer statusCode;

    private Long responseTime;

    private Boolean success;

    private LocalDateTime executedAt;

}
