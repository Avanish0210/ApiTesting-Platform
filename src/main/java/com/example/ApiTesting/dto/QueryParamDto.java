package com.example.ApiTesting.dto;

import lombok.Data;

@Data
public class QueryParamDto {

    private String key;

    private String value;

    private boolean enabled = true;
}
