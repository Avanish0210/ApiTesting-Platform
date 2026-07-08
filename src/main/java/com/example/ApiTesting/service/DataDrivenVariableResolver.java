package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.DatasetRow;

public interface DataDrivenVariableResolver {

    ApiTestRequest resolve(
            ApiTestRequest request,
            DatasetRow row);

}
