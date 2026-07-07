package com.example.ApiTesting.dto;

import com.example.ApiTesting.entity.AuthType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.HttpMethod;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiTestRequest {

    @NotNull(message = "HTTP Method is required")
    private HttpMethod httpMethod;

    @NotBlank(message = "URL cannot be empty")
    private String url;

    private List<HeaderDto> headers;

    private List<QueryParamDto> queryParams;

    private List<CookieDto> cookies;

    private List<AssertionDto> assertions;

    private AuthDto authentication;

    private BodyDto body;

    private Long responseTime;

    private Integer timeout = 30_000; // milliseconds

    private Boolean followRedirects = true;
}
