package com.example.ApiTesting.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FailedApiResponse {

    private UUID requestId;

    private String requestName;

    private Long failures;

    private Long executions;

    private Double failureRate;

}
