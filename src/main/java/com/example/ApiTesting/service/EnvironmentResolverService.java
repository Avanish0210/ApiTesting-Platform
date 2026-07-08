package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;

public interface EnvironmentResolverService {

    ApiTestRequest resolve(ApiTestRequest request);

}
