package com.example.ApiTesting.service;


import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.dto.CreateSavedRequestRequest;
import com.example.ApiTesting.dto.SavedRequestResponse;
import com.example.ApiTesting.dto.UpdateSavedRequestRequest;

import java.util.List;
import java.util.UUID;

public interface SavedRequestService {

    SavedRequestResponse create(
            UUID collectionId,
            CreateSavedRequestRequest request);

    SavedRequestResponse update(
            UUID requestId,
            UpdateSavedRequestRequest request);

    SavedRequestResponse findById(
            UUID requestId);

    List<SavedRequestResponse> findAll(
            UUID collectionId);

    void delete(
            UUID requestId);

    ApiTestResponse execute(UUID requestId);
}
