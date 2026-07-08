package com.example.ApiTesting.dto;

import lombok.Data;
import java.util.List;

@Data
public class UpdateEnvironmentRequest {

    private String name;

    private String description;

    private List<EnvironmentVariableDto> variables;

}
