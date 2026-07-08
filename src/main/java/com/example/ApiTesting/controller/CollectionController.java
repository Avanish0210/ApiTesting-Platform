package com.example.ApiTesting.controller;

import com.example.ApiTesting.dto.CollectionResponse;
import com.example.ApiTesting.dto.CreateCollectionRequest;
import com.example.ApiTesting.service.CollectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<CollectionResponse> createCollection(@Valid @RequestBody CreateCollectionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(collectionService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponse>> findAll(){
        return ResponseEntity.ok(collectionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectionResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(collectionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CollectionResponse> update(@PathVariable UUID id, @Valid @RequestBody CreateCollectionRequest request){
        return ResponseEntity.ok(collectionService.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id){

        collectionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
