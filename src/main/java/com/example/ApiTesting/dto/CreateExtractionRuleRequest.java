package com.example.ApiTesting.dto;

import lombok.Data;

@Data
public class CreateExtractionRuleRequest {

    private String variableName;

    private String jsonPath;

    private Boolean enabled = true;

}
