package com.example.ApiTesting.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "execution_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExecutionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "saved_request_id", nullable = false)
    private SavedRequest savedRequest;

    @Column(nullable = false)
    private Integer statusCode;

    @Column(nullable = false)
    private Long responseTime;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String responseBody;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String responseHeaders;

    @Column(nullable = false)
    private Boolean success;

    @Column(nullable = false)
    private Integer passedAssertions;

    @Column(nullable = false)
    private Integer failedAssertions;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String assertionResults;

    @Column(nullable = false)
    private LocalDateTime executedAt;

    @PrePersist
    public void onCreate() {
        executedAt = LocalDateTime.now();
    }
}
