package com.example.ApiTesting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "collection_runs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionRun {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    private Integer totalRequests;

    private Integer successfulRequests;

    private Integer failedRequests;

    private Long totalExecutionTime;

    @Enumerated(EnumType.STRING)
    private RunStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @OneToMany(
            mappedBy = "run",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<CollectionRunItem> items = new ArrayList<>();

}
