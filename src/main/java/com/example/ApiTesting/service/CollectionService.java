package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CollectionResponse;
import com.example.ApiTesting.dto.CreateCollectionRequest;

import java.util.List;
import java.util.UUID;

public interface CollectionService {

    CollectionResponse create(CreateCollectionRequest request);

    List<CollectionResponse> findAll();

    CollectionResponse findById(UUID id);

    CollectionResponse update(UUID id, CreateCollectionRequest request);

    void delete(UUID id);

}
