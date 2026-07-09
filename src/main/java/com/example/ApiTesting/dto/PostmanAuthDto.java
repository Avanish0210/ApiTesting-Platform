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
public class PostmanAuthDto {

    private String type;

    private List<PostmanAuthAttributeDto> bearer;

    private List<PostmanAuthAttributeDto> basic;

    private List<PostmanAuthAttributeDto> apikey;

}
