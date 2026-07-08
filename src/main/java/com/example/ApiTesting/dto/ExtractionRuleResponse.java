package com.example.ApiTesting.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ExtractionRuleResponse {

    private UUID id;

    private String variableName;

    private String jsonPath;

    private Boolean enabled;

}
