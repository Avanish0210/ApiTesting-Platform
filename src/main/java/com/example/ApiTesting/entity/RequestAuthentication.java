package com.example.ApiTesting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "request_authentication")
@Getter
@Setter
public class RequestAuthentication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private AuthType authType;

    private String username;

    private String password;

    @Column(length = 3000)
    private String bearerToken;

    private String apiKeyValue;

    private String apiKeyName;

    @Enumerated(EnumType.STRING)
    private ApiKeyLocation apiKeyLocation;

    @OneToOne
    @JoinColumn(name = "request_id")
    private SavedRequest request;
}
