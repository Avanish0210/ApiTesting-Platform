package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.CollectionRunResponse;
import com.example.ApiTesting.service.CollectionRunnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collection-runs")
public class CollectionRunnerController {

    private final CollectionRunnerService collectionRunnerService;

    @PostMapping("/{collectionId}")
    public ResponseEntity<CollectionRunResponse> run(@PathVariable UUID collectionId) {
        return ResponseEntity.ok(collectionRunnerService.run(collectionId));
    }

    @GetMapping("/{collectionId}/history")
    public ResponseEntity<List<CollectionRunResponse>> history(@PathVariable UUID collectionId) {
        return ResponseEntity.ok(collectionRunnerService.history(collectionId));

    }

}
