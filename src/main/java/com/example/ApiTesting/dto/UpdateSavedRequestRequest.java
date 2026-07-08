package com.example.ApiTesting.dto;

import lombok.*;

import org.springframework.http.HttpMethod;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSavedRequestRequest {

    private String name;

    private HttpMethod httpMethod;

    private String url;

    private Integer timeout;

    private Boolean followRedirects;

    private List<HeaderDto> headers;

    private List<QueryParamDto> queryParameters;

    private List<CookieDto> cookies;

    private AuthDto authentication;

    private BodyDto body;

    private List<AssertionDto> assertions;
}
