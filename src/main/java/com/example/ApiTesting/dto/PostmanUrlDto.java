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
public class PostmanUrlDto {

    private String raw;

    private String protocol;

    private List<String> host;

    private List<String> path;

    private List<PostmanQueryParamDto> query;

}
