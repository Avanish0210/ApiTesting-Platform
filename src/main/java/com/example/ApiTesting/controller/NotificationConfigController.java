package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.CreateNotificationConfigRequest;
import com.example.ApiTesting.dto.NotificationConfigResponse;
import com.example.ApiTesting.dto.UpdateNotificationConfigRequest;
import com.example.ApiTesting.service.NotificationConfigService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationConfigController {

    private final NotificationConfigService service;

    @PostMapping
    public ResponseEntity<NotificationConfigResponse> create(
            @RequestBody @Valid CreateNotificationConfigRequest request) {

        return ResponseEntity.ok(service.create(request));
    }

    @GetMapping
    public ResponseEntity<List<NotificationConfigResponse>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationConfigResponse> findById(
            @PathVariable UUID id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationConfigResponse> update(
            @PathVariable UUID id,
            @RequestBody UpdateNotificationConfigRequest request) {

        return ResponseEntity.ok(
                service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<NotificationConfigResponse> enable(
            @PathVariable UUID id) {

        return ResponseEntity.ok(service.enable(id));
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<NotificationConfigResponse> disable(
            @PathVariable UUID id) {

        return ResponseEntity.ok(service.disable(id));
    }

    @PostMapping("/{id}/test")
    public ResponseEntity<Void> test(
            @PathVariable UUID id) {
        service.test(id);

        return ResponseEntity.ok().build();

    }
}
