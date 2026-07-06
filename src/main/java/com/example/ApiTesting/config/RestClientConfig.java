package com.example.ApiTesting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;

import java.rmi.ServerException;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient restClient() {
        return RestClient
                .builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }
}
