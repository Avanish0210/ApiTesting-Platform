package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.DataDrivenExecutionResponse;
import com.example.ApiTesting.service.DataDrivenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/data-driven")
public class DataDrivenController {

    private final DataDrivenService service;

    @PostMapping("/{requestId}")
    public ResponseEntity<DataDrivenExecutionResponse> execute(
            @PathVariable UUID requestId,
            @RequestParam MultipartFile file)
            throws IOException {

        return ResponseEntity.ok(
                service.execute(requestId, file)
        );
    }
}
