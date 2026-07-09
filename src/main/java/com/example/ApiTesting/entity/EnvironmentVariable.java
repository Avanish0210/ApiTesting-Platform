package com.example.ApiTesting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "environment_variables")
@Getter
@Setter
public class EnvironmentVariable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String variableKey;

    @Lob
    private String variableValue;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(nullable = false)
    private Boolean secret = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "environment_id")
    private Environment environment;

}
