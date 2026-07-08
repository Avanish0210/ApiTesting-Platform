package com.example.ApiTesting.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SlowApiResponse {

    private UUID requestId;

    private String requestName;

    private Double averageResponseTime;

    private Long executions;

}
