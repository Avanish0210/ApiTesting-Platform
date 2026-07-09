package com.example.ApiTesting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostmanRequestDto {

    private String method;

    private List<PostmanHeaderDto> header;

    private PostmanUrlDto url;

    private PostmanBodyDto body;

    private PostmanAuthDto auth;

}