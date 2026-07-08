package com.example.ApiTesting.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HeaderDto {
    private String key;
    private String value;
    private Boolean enabled = true;
}
