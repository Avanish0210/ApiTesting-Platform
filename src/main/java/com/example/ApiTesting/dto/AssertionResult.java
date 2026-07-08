package com.example.ApiTesting.dto;

import com.example.ApiTesting.entity.AssertionType;
import lombok.Data;

@Data
public class AssertionResult {

    private AssertionType type;
    private boolean passed;
    private String expected;
    private String actual;
    private String message;
    private String assertion;
}
