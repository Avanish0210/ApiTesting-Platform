package com.example.ApiTesting.service;

import java.util.UUID;

public interface PostmanExportService {
    byte[] exportCollection(UUID collectionId);
}
