package com.example.ApiTesting.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostmanItemDto {

    private String name;

    private PostmanRequestDto request;

    private List<PostmanItemDto> item;
}
