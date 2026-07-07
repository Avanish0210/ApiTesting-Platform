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
    private String message;
    private Long responseTime;
}
