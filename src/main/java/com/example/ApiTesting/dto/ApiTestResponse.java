package com.example.ApiTesting.dto;

import lombok.*;

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
    private Map<String, String> headers;
    private String message;
}
