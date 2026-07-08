package com.example.ApiTesting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "collection_run_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionRunItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_run_id")
    private CollectionRun run;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saved_request_id")
    private SavedRequest request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "execution_history_id")
    private ExecutionHistory executionHistory;

    private Integer executionOrder;

    private Boolean success;

    private Long responseTime;

    private Integer statusCode;
}
