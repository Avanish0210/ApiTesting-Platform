package com.example.ApiTesting.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "request_body")
@Getter
@Setter
public class RequestBody {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private BodyType bodyType;

    @Enumerated(EnumType.STRING)
    private RawType rawType;

    @Lob
    private String rawBody;

    private String binaryFilePath;

    @OneToOne
    @JoinColumn(name = "request_id")
    private SavedRequest request;

    @OneToMany(mappedBy = "body",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<FormData> formData = new ArrayList<>();
}
