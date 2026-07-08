package com.example.ApiTesting.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "saved_requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;


    private HttpMethod httpMethod;

    @Column(length = 3000)
    private String url;

    private Integer timeout;

    private Boolean followRedirects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RequestHeader> headers = new ArrayList<>();

    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<QueryParameter> queryParameters = new ArrayList<>();

    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RequestCookie> cookies = new ArrayList<>();

    @OneToMany(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<RequestAssertion> assertions = new ArrayList<>();

    @OneToOne(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private RequestAuthentication authentication;

    @OneToOne(mappedBy = "request",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private RequestBody body;

    @OneToMany(
            mappedBy = "savedRequest",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ExecutionHistory> executionHistories = new ArrayList<>();

    @OneToMany(
            mappedBy = "savedRequest",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ExtractionRule> extractionRules = new ArrayList<>();
}
