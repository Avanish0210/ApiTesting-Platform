package com.example.ApiTesting.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostmanInfoDto {

    private String name;

    private String description;

    private String schema;
}
