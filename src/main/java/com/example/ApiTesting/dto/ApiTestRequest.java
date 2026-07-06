package com.example.ApiTesting.dto;

import lombok.*;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiTestRequest {
    private HttpMethod httpMethod;
    private String url;
    private Map<String, String> headers;
    private String body;
}
