package com.example.ApiTesting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostmanVariableDto {

    private String key;

    private String value;

    private String type;

    private boolean disabled;
}
