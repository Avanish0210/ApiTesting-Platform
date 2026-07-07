package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.ApiTestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {
    private final RestClient restClient;
    private final RequestBuilderService requestBuilder;
    private final ResponseBuilderService responseBuilder;

    public ApiTestResponse apiTest(ApiTestRequest apiTestRequest) {

        RestClient.RequestBodySpec requestBodySpec = requestBuilder.buildRequest(apiTestRequest);
        return responseBuilder.execute(requestBodySpec);
    }
}