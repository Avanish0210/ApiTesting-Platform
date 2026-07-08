package com.example.ApiTesting.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class CreateCollectionRequest {

    @NotBlank(message = "Collection name is required")
    private String name;

    private String description;
}
