package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;

public interface DynamicVariableService {

    ApiTestRequest resolve(ApiTestRequest request);

}
