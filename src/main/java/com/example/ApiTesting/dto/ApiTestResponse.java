package com.example.ApiTesting.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiTestResponse {
    private int status;
    private String responseBody;
    private List<HeaderDto> headers;
    private List<CookieDto> cookies;
    private Integer passedAssertions = 0;
    private Integer failedAssertions = 0;
    private List<AssertionResult> assertionResults;
    private boolean allPassed;
    private String message;
    private Long responseTime;
}
