package com.example.ApiTesting.dto;

import com.example.ApiTesting.entity.AssertionOperator;
import com.example.ApiTesting.entity.AssertionType;
import lombok.Data;

@Data
public class AssertionDto {

    private AssertionType type;
    private AssertionOperator operator;
    // For JSON_PATH
    private String expression;

    // Expected value
    private String expectedValue;

}
