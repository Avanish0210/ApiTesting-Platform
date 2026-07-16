package com.example.ApiTesting.dto;

import lombok.*;

import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedRequestResponse {

    private UUID id;

    private String name;

    private HttpMethod httpMethod;

    private String url;

    private Integer timeout;

    private Boolean followRedirects;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String preRequestScript;

    private String postResponseScript;
}
