package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CollectionResponse;
import com.example.ApiTesting.entity.Collection;
import org.springframework.web.multipart.MultipartFile;

public interface PostmanImportService {

    Collection importCollection(MultipartFile file);

}
