package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.DataDrivenExecutionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface DataDrivenService {

    DataDrivenExecutionResponse execute(
            UUID requestId,
            MultipartFile file)
            throws IOException;

}
