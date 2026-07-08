package com.example.ApiTesting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "request_assertions")
@Getter
@Setter
public class RequestAssertion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private AssertionType type;

    private String expression;

    private String expectedValue;

    @Enumerated(EnumType.STRING)
    private AssertionOperator operator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    private SavedRequest request;
}
