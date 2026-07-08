package com.example.ApiTesting.controller;



import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.dto.CreateSavedRequestRequest;
import com.example.ApiTesting.dto.SavedRequestResponse;
import com.example.ApiTesting.dto.UpdateSavedRequestRequest;
import com.example.ApiTesting.service.SavedRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SavedRequestController {

    private final SavedRequestService savedRequestService;

    @PostMapping("/collections/{collectionId}/requests")
    public ResponseEntity<SavedRequestResponse> createRequest(
            @PathVariable UUID collectionId,
            @Valid @RequestBody CreateSavedRequestRequest request) {

        SavedRequestResponse response =
                savedRequestService.create(collectionId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/collections/{collectionId}/requests")
    public ResponseEntity<List<SavedRequestResponse>> getAllRequests(
            @PathVariable UUID collectionId) {

        return ResponseEntity.ok(
                savedRequestService.findAll(collectionId));
    }

    @GetMapping("/requests/{requestId}")
    public ResponseEntity<SavedRequestResponse> getRequest(
            @PathVariable UUID requestId) {

        return ResponseEntity.ok(
                savedRequestService.findById(requestId));
    }

    @PutMapping("/requests/{requestId}")
    public ResponseEntity<SavedRequestResponse> updateRequest(
            @PathVariable UUID requestId,
            @Valid @RequestBody UpdateSavedRequestRequest request) {

        return ResponseEntity.ok(
                savedRequestService.update(requestId, request));
    }

    @DeleteMapping("/requests/{requestId}")
    public ResponseEntity<Void> deleteRequest(
            @PathVariable UUID requestId) {

        savedRequestService.delete(requestId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/requests/{requestId}/execute")
    public ResponseEntity<ApiTestResponse> executeRequest(
            @PathVariable UUID requestId) {

        return ResponseEntity.ok(
                savedRequestService.execute(requestId));

    }
}
