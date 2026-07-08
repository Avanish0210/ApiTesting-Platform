package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.CreateEnvironmentRequest;
import com.example.ApiTesting.dto.EnvironmentResponse;
import com.example.ApiTesting.dto.UpdateEnvironmentRequest;
import com.example.ApiTesting.service.EnvironmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/environments")
@RequiredArgsConstructor
public class EnvironmentController {

    private final EnvironmentService environmentService;

    @PostMapping
    public ResponseEntity<EnvironmentResponse> create(
            @RequestBody CreateEnvironmentRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(environmentService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<EnvironmentResponse>> findAll() {

        return ResponseEntity.ok(
                environmentService.findAll()
        );
    }

    @GetMapping("/{environmentId}")
    public ResponseEntity<EnvironmentResponse> findById(
            @PathVariable UUID environmentId) {

        return ResponseEntity.ok(
                environmentService.findById(environmentId)
        );
    }

    @GetMapping("/active")
    public ResponseEntity<EnvironmentResponse> getActiveEnvironment() {

        return ResponseEntity.ok(
                environmentService.getActiveEnvironment()
        );
    }

    @PutMapping("/{environmentId}")
    public ResponseEntity<EnvironmentResponse> update(
            @PathVariable UUID environmentId,
            @RequestBody UpdateEnvironmentRequest request) {

        return ResponseEntity.ok(
                environmentService.update(environmentId, request)
        );
    }

    @DeleteMapping("/{environmentId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID environmentId) {

        environmentService.delete(environmentId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{environmentId}/activate")
    public ResponseEntity<EnvironmentResponse> activate(
            @PathVariable UUID environmentId) {

        return ResponseEntity.ok(
                environmentService.activate(environmentId)
        );
    }
}
