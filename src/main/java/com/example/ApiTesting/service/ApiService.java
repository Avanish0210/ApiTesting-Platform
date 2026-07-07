package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.ApiTestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {
    private final RequestBuilderService requestBuilder;
    private final ResponseBuilderService responseBuilder;
    private final AssertionService assertionService;

    public ApiTestResponse apiTest(ApiTestRequest apiTestRequest) throws IOException {

        RestClient.RequestBodySpec requestBodySpec = requestBuilder.buildRequest(apiTestRequest);
        ApiTestResponse response =  responseBuilder.execute(requestBodySpec);

        assertionService.executeAssertions(apiTestRequest,response);

        return response;
    }
}