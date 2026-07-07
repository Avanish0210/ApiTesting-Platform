package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.RawType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.InvalidUrlException;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestBuilderService {
    private static final int DEFAULT_TIMEOUT = 30_000;

    public RestClient.RequestBodySpec buildRequest(ApiTestRequest request) throws IOException {
        RestClient restClient = createRestClient(request);
        RestClient.RequestBodySpec requestSpec = createRequest(restClient, request);

        buildUri(request);
        applyHeaders(requestSpec, request);
        applyAuthentication(requestSpec, request);
        applyBody(requestSpec, request);
        applyCookies(requestSpec, request);

        return requestSpec;
    }

    private RestClient createRestClient(ApiTestRequest request) {

        int timeout = Optional.ofNullable(request.getTimeout()).orElse(DEFAULT_TIMEOUT);

        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(timeout))
                .build();

        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory(httpClient))
                .build();
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

    private void applyBody(RestClient.RequestBodySpec requestSpec, ApiTestRequest request) throws IOException {
        BodyDto body = request.getBody();
        if (body == null || body.getType() == null) {
            return;
        }
        switch (body.getType()) {
            case NONE -> {
                // Nothing to send
            }
            case RAW -> applyRawBody(requestSpec, body);
            case FORM_DATA -> applyMultipart(requestSpec, body);
            case URL_ENCODED -> applyUrlEncoded(requestSpec, body);
            case BINARY -> applyBinary(requestSpec, body);
        }
    }

    private void applyBinary(RestClient.RequestBodySpec requestSpec, BodyDto body) throws IOException {
        Path path = Path.of(body.getBinaryFilePath());
        FileSystemResource resource = new FileSystemResource(path);
        String contentType = Files.probeContentType(path);
        requestSpec
                .contentType(
                        contentType != null
                                ? MediaType.parseMediaType(contentType)
                                : MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    private void applyUrlEncoded(RestClient.RequestBodySpec requestSpec, BodyDto body) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        for (QueryParamDto field : body.getUrlEncoded()) {
            if (!field.isEnabled()) {
                continue;
            }
            form.add(field.getKey(), field.getValue());
        }
        requestSpec
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form);
    }

    private void applyMultipart(RestClient.RequestBodySpec requestSpec, BodyDto body) {

        MultiValueMap<String, Object> multipart = new LinkedMultiValueMap<>();
        for (FormDataDto part : body.getFormData()) {
            if (!part.isEnabled()) {
                continue;
            }
            if (part.isFile()) {
                multipart.add(
                        part.getKey(),
                        new FileSystemResource(part.getFilePath()));
            } else {
                multipart.add(
                        part.getKey(),
                        part.getValue());
            }
        }
        requestSpec
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(multipart);
    }

    private void applyRawBody(RestClient.RequestBodySpec requestSpec,
                              BodyDto body) {

        MediaType mediaType = resolveMediaType(body.getRawType());

        requestSpec
                .contentType(mediaType)
                .body(body.getRaw());
    }
    private MediaType resolveMediaType(RawType rawType) {
        if (rawType == null) {
            return MediaType.TEXT_PLAIN;
        }
        return switch (rawType) {
            case JSON -> MediaType.APPLICATION_JSON;
            case XML -> MediaType.APPLICATION_XML;
            case HTML -> MediaType.TEXT_HTML;
            case TEXT -> MediaType.TEXT_PLAIN;
            case JAVASCRIPT -> MediaType.valueOf("application/javascript");
        };
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

    private void applyCookies(RestClient.RequestBodySpec requestSpec, ApiTestRequest request) {
        if (request.getCookies() == null || request.getCookies().isEmpty()) {
            return;
        }

        StringBuilder cookieHeader = new StringBuilder();
        for (CookieDto cookie : request.getCookies()) {
            if (!cookie.isEnabled()) {
                continue;
            }
            if (cookie.getName() == null || cookie.getName().isBlank()) {
                continue;
            }
            if (!cookieHeader.isEmpty()) {
                cookieHeader.append("; ");
            }
            cookieHeader.append(cookie.getName()).append("=").append(cookie.getValue());
        }

        if (!cookieHeader.isEmpty()) {
            requestSpec.headers(headers -> headers.add("Cookie", cookieHeader.toString()));
        }
    }

}
