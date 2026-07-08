package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CollectionRunResponse;

import java.util.List;
import java.util.UUID;

public interface CollectionRunnerService {

    CollectionRunResponse run(UUID collectionId);
    List<CollectionRunResponse> history(UUID collectionId);

}
