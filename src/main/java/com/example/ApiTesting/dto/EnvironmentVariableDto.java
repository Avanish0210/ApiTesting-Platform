package com.example.ApiTesting.dto;

import lombok.Data;

@Data
public class EnvironmentVariableDto {

    private String variableKey;

    private String variableValue;

    private Boolean enabled;

    private Boolean secret;

}
