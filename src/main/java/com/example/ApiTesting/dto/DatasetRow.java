package com.example.ApiTesting.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DatasetRow {
    private Map<String, String> values;

}
