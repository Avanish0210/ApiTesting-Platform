package com.example.ApiTesting.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateEnvironmentRequest {

    private String name;

    private String description;

    private List<EnvironmentVariableDto> variables;

}
