package com.example.ApiTesting.dto;

import lombok.Data;

@Data
public class ExtractionRuleDto {

    private String variableName;

    private String jsonPath;

    private Boolean enabled;

}
