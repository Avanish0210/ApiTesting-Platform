package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.dto.HeaderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResponseBuilderService {

    private final RestClient restClient;

    public ApiTestResponse execute(RestClient.RequestBodySpec requestSpec) {
        long start = System.nanoTime();

        ApiTestResponse response = requestSpec.exchange((clientRequest, clientResponse) -> {
            ApiTestResponse apiResponse = new ApiTestResponse();
            buildStatus(apiResponse, clientResponse);
            buildHeaders(apiResponse, clientResponse);
            buildBody(apiResponse, clientResponse);

            return apiResponse;
        });

        long end = System.nanoTime();
        apiResponseTime(response, start, end);

        return response;
    }

    private void buildStatus(ApiTestResponse response, ClientHttpResponse clientResponse) throws IOException {
        response.setStatus(clientResponse.getStatusCode().value());
    }

    private void buildHeaders(ApiTestResponse response, ClientHttpResponse clientResponse) throws IOException {
        List<HeaderDto> headers = new ArrayList<>();

        clientResponse.getHeaders().forEach((key, values) -> {
            HeaderDto header = new HeaderDto();
            header.setKey(key);
            header.setValue(String.join(", ", values));
            headers.add(header);
        });

        response.setHeaders(headers);
    }
    private void buildBody(ApiTestResponse response, ClientHttpResponse clientResponse) throws IOException {
        String body = new String(clientResponse.getBody().readAllBytes());
        response.setResponseBody(body);
    }

    private void apiResponseTime(ApiTestResponse response, long start, long end) {
        response.setResponseTime((end - start) / 1_000_000);
    }
}
