package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.CreateScheduleRequest;
import com.example.ApiTesting.dto.ScheduleExecutionResponse;
import com.example.ApiTesting.dto.ScheduleResponse;
import com.example.ApiTesting.dto.UpdateScheduleRequest;
import com.example.ApiTesting.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> create(
            @Valid @RequestBody CreateScheduleRequest request) {

        return ResponseEntity.ok(
                scheduleService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                scheduleService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponse>> findAll() {

        return ResponseEntity.ok(
                scheduleService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponse> update(
            @PathVariable UUID id,
            @RequestBody UpdateScheduleRequest request) {

        return ResponseEntity.ok(
                scheduleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {

        scheduleService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<ScheduleResponse> enable(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                scheduleService.enable(id));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<ScheduleResponse> disable(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                scheduleService.disable(id));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<ScheduleExecutionResponse>> history(
            @PathVariable UUID id) {

        return ResponseEntity.ok(
                scheduleService.executionHistory(id));
    }
}
