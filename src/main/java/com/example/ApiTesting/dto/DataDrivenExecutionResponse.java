package com.example.ApiTesting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataDrivenExecutionResponse {

    private Integer totalRows;

    private Integer successfulRows;

    private Integer failedRows;

}
