package com.example.ApiTesting.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateScheduleRequest {

    private String name;

    private String cronExpression;

    private Boolean enabled;
}
