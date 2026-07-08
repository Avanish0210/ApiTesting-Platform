package com.example.ApiTesting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateScheduleRequest {

    @NotBlank
    private String name;

    @NotNull
    private UUID collectionId;

    @NotBlank
    private String cronExpression;
}
