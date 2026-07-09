package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.Collection;
import com.example.ApiTesting.entity.SavedRequest;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.repository.CollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostmanExportServiceImpl
        implements PostmanExportService {

    private final CollectionRepository collectionRepository;

    private final ObjectMapper objectMapper;

    @Override
    public byte[] exportCollection(UUID collectionId) {
        Collection collection =
                collectionRepository.findById(collectionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Collection not found"));
        PostmanCollectionDto dto =
                new PostmanCollectionDto();

        PostmanInfoDto info =
                new PostmanInfoDto();

        info.setName(collection.getName());
        info.setDescription(collection.getDescription());

        info.setSchema(
                "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
        );

        dto.setInfo(info);
        List<PostmanItemDto> items = new ArrayList<>();
        for (SavedRequest request : collection.getRequests()) {

            PostmanItemDto item = new PostmanItemDto();

            item.setName(request.getName());

            PostmanRequestDto req =
                    new PostmanRequestDto();

            req.setMethod(String.valueOf(request.getHttpMethod()));

            PostmanUrlDto url =
                    new PostmanUrlDto();

            url.setRaw(request.getUrl());

            req.setUrl(url);

            item.setRequest(req);

            items.add(item);
        }
        dto.setItem(items);

        try {

            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsBytes(dto);

        }
        catch (Exception ex) {

            throw new RuntimeException(ex);

        }

    }

}
