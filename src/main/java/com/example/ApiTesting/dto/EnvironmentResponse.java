package com.example.ApiTesting.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EnvironmentResponse {

    private UUID id;

    private String name;

    private String description;

    private Boolean active;

    private List<EnvironmentVariableDto> variables;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
