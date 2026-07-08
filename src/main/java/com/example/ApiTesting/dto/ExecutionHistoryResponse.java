package com.example.ApiTesting.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionHistoryResponse {

    private UUID id;

    private Integer statusCode;

    private Long responseTime;

    private String responseBody;

    private String responseHeaders;

    private Boolean success;

    private Integer passedAssertions;

    private List<HeaderDto> headers;

    private Integer failedAssertions;

    private List<AssertionResult> assertionResults;

    private LocalDateTime executedAt;

}
