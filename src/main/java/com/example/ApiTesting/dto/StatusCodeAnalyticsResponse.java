package com.example.ApiTesting.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusCodeAnalyticsResponse {

    private Integer statusCode;

    private Long count;

}
