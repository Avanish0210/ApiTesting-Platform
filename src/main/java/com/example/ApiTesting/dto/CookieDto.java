package com.example.ApiTesting.dto;

import lombok.Data;

@Data
public class CookieDto {

    private String name;
    private String value;
    private boolean enabled = true;
}
