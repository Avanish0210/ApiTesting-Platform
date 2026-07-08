package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.DataDrivenExecutionResponse;
import com.example.ApiTesting.dto.DatasetRow;
import com.example.ApiTesting.entity.SavedRequest;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.SavedRequestMapper;
import com.example.ApiTesting.repository.SavedRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DataDrivenServiceImpl implements DataDrivenService {
    private final SavedRequestRepository repository;

    private final SavedRequestMapper mapper;

    private final CsvDatasetParser parser;

    private final ApiService apiService;
    private final DataDrivenVariableResolver resolver;

    @Override
    public DataDrivenExecutionResponse execute(
            UUID requestId,
            MultipartFile file)
            throws IOException {

        SavedRequest savedRequest =
                repository.findById(requestId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Request not found"));
        List<DatasetRow> rows =
                parser.parse(file.getBytes());
        int success = 0;

        int failed = 0;
        for (DatasetRow row : rows) {

            ApiTestRequest request =
                    mapper.toApiRequest(savedRequest);

            request =
                    resolver.resolve(
                            request,
                            row);

            try {

                apiService.execute(
                        request,
                        savedRequest);

                success++;

            } catch (Exception ex) {

                failed++;

            }

        }
        return DataDrivenExecutionResponse.builder()
                .totalRows(rows.size())
                .successfulRows(success)
                .failedRows(failed)
                .build();
    }



}
