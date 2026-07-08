package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.entity.SavedRequest;

public interface VariableExtractionService {

    void extract(
            SavedRequest savedRequest,
            ApiTestResponse response);

}
