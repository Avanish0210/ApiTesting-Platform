package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.service.ApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
@Slf4j
public class ApiTestController {

    private final ApiService apiService;

    @PostMapping
    private ResponseEntity<ApiTestResponse> apiTest(@Valid @RequestBody ApiTestRequest apiTestRequest) {
        ApiTestResponse apiTestResponse = apiService.apiTest(apiTestRequest);
        return ResponseEntity.status(apiTestResponse.getStatus()).body(apiTestResponse);
    }
}
