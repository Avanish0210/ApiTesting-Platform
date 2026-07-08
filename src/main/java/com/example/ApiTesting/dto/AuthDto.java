package com.example.ApiTesting.dto;

import com.example.ApiTesting.entity.ApiKeyLocation;
import com.example.ApiTesting.entity.AuthType;
import lombok.Data;

@Data
public class AuthDto {

    private AuthType type;
    private String username;
    private String password;
    private String token;
    private String apiKeyHeaderName;
    private ApiKeyLocation apiKeyLocation;
    private String apiKeyValue;
}
