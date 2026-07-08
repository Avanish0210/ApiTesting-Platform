package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.dto.ExecutionHistoryFilter;
import com.example.ApiTesting.dto.ExecutionHistoryResponse;
import com.example.ApiTesting.dto.ExecutionStatisticsResponse;
import com.example.ApiTesting.service.ApiService;
import com.example.ApiTesting.service.ExecutionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/history")
public class ExecutionHistoryController {

    private final ExecutionHistoryService historyService;
    private final ApiService apiService;

    @GetMapping("/{historyId}")
    public ResponseEntity<ExecutionHistoryResponse> findById(
            @PathVariable UUID historyId) {

        return ResponseEntity.ok(
                historyService.findById(historyId)
        );
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<Page<ExecutionHistoryResponse>> findByRequest(

            @PathVariable UUID requestId,

            ExecutionHistoryFilter filter,

            @PageableDefault(
                    size = 20,
                    sort = "executedAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
            ) {

        return ResponseEntity.ok(
                historyService.findByRequest(
                        requestId,
                        filter,
                        pageable
                )
        );
    }

    @DeleteMapping("/{historyId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID historyId) {

        historyService.delete(historyId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/request/{requestId}")
    public ResponseEntity<Void> deleteAll(
            @PathVariable UUID requestId) {

        historyService.deleteAll(requestId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/request/{requestId}/statistics")
    public ResponseEntity<ExecutionStatisticsResponse> statistics(
            @PathVariable UUID requestId) {

        return ResponseEntity.ok(
                historyService.getStatistics(requestId)
        );
    }
    @PostMapping("/{historyId}/rerun")
    public ResponseEntity<ApiTestResponse> rerun(
            @PathVariable UUID historyId) {

        return ResponseEntity.ok(
                apiService.rerun(historyId)
        );

    }
    @GetMapping("/request/{requestId}/export/json")
    public ResponseEntity<String> exportJson(
            @PathVariable UUID requestId) {

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=history.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(historyService.exportJson(requestId));
    }
    @GetMapping("/request/{requestId}/export/csv")
    public ResponseEntity<String> exportCsv(
            @PathVariable UUID requestId) {

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=history.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(historyService.exportCsv(requestId));
    }

}
