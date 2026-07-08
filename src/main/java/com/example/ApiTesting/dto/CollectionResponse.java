package com.example.ApiTesting.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CollectionResponse {

    private UUID id;

    private String name;

    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
