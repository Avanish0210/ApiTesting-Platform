package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.CollectionResponse;
import com.example.ApiTesting.entity.Collection;
import com.example.ApiTesting.service.PostmanExportService;
import com.example.ApiTesting.service.PostmanImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/postman")
@RequiredArgsConstructor
public class PostmanController {

    private final PostmanImportService postmanImportService;

    @PostMapping("/import")
    public ResponseEntity<Collection> importCollection(
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postmanImportService.importCollection(file));
    }

    private final PostmanExportService exportService;

    @GetMapping("/export/{collectionId}")
    public ResponseEntity<byte[]> export(
            @PathVariable UUID collectionId) {

        byte[] json =
                exportService.exportCollection(collectionId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=collection.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(json);

    }

}
