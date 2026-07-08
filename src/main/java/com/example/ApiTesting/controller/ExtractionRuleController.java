package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.CreateExtractionRuleRequest;
import com.example.ApiTesting.dto.ExtractionRuleResponse;
import com.example.ApiTesting.dto.UpdateExtractionRuleRequest;
import com.example.ApiTesting.service.ExtractionRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/extraction-rules")
@RequiredArgsConstructor
public class ExtractionRuleController {

    private final ExtractionRuleService service;

    @PostMapping("/request/{requestId}")
    public ResponseEntity<ExtractionRuleResponse> create(
            @PathVariable UUID requestId,
            @RequestBody CreateExtractionRuleRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.create(requestId, request));
    }

    @GetMapping("/{ruleId}")
    public ResponseEntity<ExtractionRuleResponse> findById(
            @PathVariable UUID ruleId) {

        return ResponseEntity.ok(
                service.findById(ruleId));
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<List<ExtractionRuleResponse>> findByRequest(
            @PathVariable UUID requestId) {

        return ResponseEntity.ok(
                service.findByRequest(requestId));
    }

    @PutMapping("/{ruleId}")
    public ResponseEntity<ExtractionRuleResponse> update(
            @PathVariable UUID ruleId,
            @RequestBody UpdateExtractionRuleRequest request) {

        return ResponseEntity.ok(
                service.update(ruleId, request));
    }

    @DeleteMapping("/{ruleId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID ruleId) {

        service.delete(ruleId);

        return ResponseEntity.noContent().build();
    }
}
