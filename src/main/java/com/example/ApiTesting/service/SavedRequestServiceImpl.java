package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.Collection;
import com.example.ApiTesting.entity.SavedRequest;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.SavedRequestMapper;
import com.example.ApiTesting.repository.CollectionRepository;
import com.example.ApiTesting.repository.SavedRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SavedRequestServiceImpl implements SavedRequestService {

    private final SavedRequestRepository savedRequestRepository;
    private final CollectionRepository collectionRepository;
    private final SavedRequestMapper mapper;
    private final ApiService apiService;

    @Override
    public SavedRequestResponse create(
            UUID collectionId,
            CreateSavedRequestRequest dto) {

        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Collection not found"));

        SavedRequest request = mapper.toEntity(dto, collection);

        savedRequestRepository.save(request);

        return mapper.toResponse(request);
    }

    @Override
    @Transactional
    public SavedRequestResponse findById(UUID requestId) {

        SavedRequest request = savedRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Request not found"));

        return mapper.toResponse(request);
    }

    @Override
    @Transactional
    public List<SavedRequestResponse> findAll(UUID collectionId) {

        return savedRequestRepository
                .findByCollectionId(collectionId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID requestId) {

        SavedRequest request = savedRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Request not found"));

        savedRequestRepository.delete(request);
    }

    @Override
    public SavedRequestResponse update(
            UUID requestId,
            UpdateSavedRequestRequest dto) {

        SavedRequest request = savedRequestRepository.findById(requestId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Request not found"));

        request.setName(dto.getName());
        request.setHttpMethod(dto.getHttpMethod());
        request.setUrl(dto.getUrl());
        request.setTimeout(dto.getTimeout());
        request.setFollowRedirects(dto.getFollowRedirects());

        request.setPreRequestScript(dto.getPreRequestScript());
        request.setPostResponseScript(dto.getPostResponseScript());

        savedRequestRepository.save(request);

        return mapper.toResponse(request);
    }

    @Override
    public ApiTestResponse execute(UUID requestId) {

        SavedRequest savedRequest =
                savedRequestRepository.findById(requestId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Request not found"));

        ApiTestRequest apiRequest =
                mapper.toApiRequest(savedRequest);

        try {
            return apiService.apiTest(apiRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
