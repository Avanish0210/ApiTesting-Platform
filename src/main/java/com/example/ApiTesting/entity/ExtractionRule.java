package com.example.ApiTesting.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "extraction_rules")
@Getter
@Setter
public class ExtractionRule {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String variableName;

    @Column(nullable = false)
    private String jsonPath;

    @Column(nullable = false)
    private Boolean enabled = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saved_request_id")
    private SavedRequest savedRequest;

}
