package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.ApiTestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.lang.reflect.Type;
import java.net.URI;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final RestClient restClient;
    public ApiTestResponse apiTest(ApiTestRequest apiTestRequest) {

        //Logic will come here
        //Build Http request
        //execute request using restClient
        //capture response
        //return response


        URI uri = URI.create(apiTestRequest.getUrl());

        //build uri and method
        RestClient.RequestBodySpec requestBodySpec = restClient
                .method(apiTestRequest.getHttpMethod())
                .uri(uri);

        //add header
        if(apiTestRequest.getHeaders() != null) {
            requestBodySpec.headers(httpHeaders -> httpHeaders.setAll(apiTestRequest.getHeaders()));
        }
        //add Body
        if(apiTestRequest.getBody() != null && !apiTestRequest.getBody().isBlank()) {
                requestBodySpec.contentType(MediaType.APPLICATION_JSON);
                requestBodySpec.body(apiTestRequest.getBody());
        }

        //Get request is dont have body will other may have

        return requestBodySpec.exchange((clientRequest, clientResponse) -> {
            ApiTestResponse apiTestResponse = new ApiTestResponse();
            apiTestResponse.setStatus(clientResponse.getStatusCode().value());
            apiTestResponse.setResponseBody(
                    new String(clientResponse.getBody().readAllBytes())
            );
            apiTestResponse.setHeaders(clientResponse.getHeaders().toSingleValueMap());

            return apiTestResponse;
        });
    }
}
