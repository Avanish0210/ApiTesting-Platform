package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.AuthDto;
import com.example.ApiTesting.dto.HeaderDto;
import com.example.ApiTesting.dto.QueryParamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.InvalidUrlException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestBuilderService {
    private final RestClient restClient;

    public RestClient.RequestBodySpec buildRequest(ApiTestRequest request) {

        RestClient.RequestBodySpec requestSpec = createRequest(restClient, request);

        buildUri(request);
        applyHeaders(requestSpec, request);
        applyAuthentication(requestSpec, request);
        applyBody(requestSpec, request);

        return requestSpec;
    }

    private URI validateUrl(String url) {
        try {
            return URI.create(url);
        } catch (IllegalArgumentException ex) {
            throw new InvalidUrlException("Invalid URL: " + url);
        }
    }

    private RestClient.RequestBodySpec createRequest(RestClient restClient, ApiTestRequest apiTestRequest) {

        return restClient
                .method(apiTestRequest.getHttpMethod())
                .uri(buildUri(apiTestRequest));
    }

    private void applyHeaders(RestClient.RequestBodySpec requestSpec, ApiTestRequest request) {
        List<HeaderDto> headers = request.getHeaders();
        if(headers == null || headers.isEmpty()){
            return;
        }

        requestSpec.headers(httpHeaders -> {
            for (HeaderDto header : headers) {
                if (!header.isEnabled()) {
                    continue;
                }
                if (header.getKey() == null || header.getKey().isBlank()) {
                    continue;
                }
                httpHeaders.add(header.getKey(), header.getValue());
            }
        });
    }

    private void applyBody(RestClient.RequestBodySpec requestSpec, ApiTestRequest request) {

        if (request.getBody() == null || request.getBody().isBlank()) {
            return;
        }
        requestSpec
                .contentType(MediaType.APPLICATION_JSON)
                .body(request.getBody());
    }

    private URI buildUri(ApiTestRequest request) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(request.getUrl());

        if (request.getQueryParams() != null) {
            request.getQueryParams()
                    .stream()
                    .filter(QueryParamDto::isEnabled)
                    .forEach(param ->
                            builder.queryParam(
                                    param.getKey(),
                                    param.getValue()));
        }

        return builder.build().toUri();
    }

    private void applyAuthentication(RestClient.RequestBodySpec requestSpec, ApiTestRequest request) {

        AuthDto auth = request.getAuthentication();
        if (auth == null || auth.getType() == null) {
            return;
        }
        switch (auth.getType()) {
            case NONE -> {
                // Do nothing
            }
            case BEARER -> requestSpec.headers(headers ->
                    headers.setBearerAuth(auth.getToken()));
            case BASIC -> requestSpec.headers(headers ->
                    headers.setBasicAuth(
                            auth.getUsername(),
                            auth.getPassword()));
            case API_KEY -> requestSpec.headers(headers ->
                    headers.add(auth.getApiKeyHeaderName(), auth.getApiKeyValue()));
        }
    }
}
