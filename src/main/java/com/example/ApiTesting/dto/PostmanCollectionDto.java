package com.example.ApiTesting.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostmanCollectionDto {

    private PostmanInfoDto info;

    private List<PostmanItemDto> item;
    private List<PostmanVariableDto> variable;
}
