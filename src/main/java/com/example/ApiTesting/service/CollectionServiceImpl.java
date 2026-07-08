package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CollectionResponse;
import com.example.ApiTesting.dto.CreateCollectionRequest;
import com.example.ApiTesting.entity.Collection;
import com.example.ApiTesting.exception.DuplicateResourceException;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.SavedRequestMapper;
import com.example.ApiTesting.repository.CollectionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository repository;
    private final ApiService apiService;
    private final SavedRequestMapper savedRequestMapper;

    @Override
    public CollectionResponse create(CreateCollectionRequest request) {

        if(repository.existsByNameIgnoreCase(request.getName())){
            throw new DuplicateResourceException("Collection already exists.");
        }

        Collection collection = Collection.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        repository.save(collection);

        return mapToResponse(collection);
    }

    @Override
    @Transactional
    public List<CollectionResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public CollectionResponse findById(UUID id) {

        Collection collection = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Collection not found."));

        return mapToResponse(collection);
    }

    @Override
    public CollectionResponse update(UUID id,
                                     CreateCollectionRequest request) {

        Collection collection = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Collection not found."));

        collection.setName(request.getName());
        collection.setDescription(request.getDescription());

        repository.save(collection);

        return mapToResponse(collection);
    }

    @Override
    public void delete(UUID id) {

        Collection collection = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Collection not found."));

        repository.delete(collection);
    }

    private CollectionResponse mapToResponse(Collection collection){

        return CollectionResponse.builder()
                .id(collection.getId())
                .name(collection.getName())
                .description(collection.getDescription())
                .createdAt(collection.getCreatedAt())
                .updatedAt(collection.getUpdatedAt())
                .build();
    }


}
