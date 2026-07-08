package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.entity.ExecutionHistory;
import com.example.ApiTesting.entity.SavedRequest;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.SavedRequestMapper;
import com.example.ApiTesting.repository.ExecutionHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class ApiService {
    private final RequestBuilderService requestBuilder;
    private final ResponseBuilderService responseBuilder;
    private final AssertionService assertionService;
    private final ExecutionHistoryService historyService;
    private final ExecutionHistoryRepository executionHistoryRepository;
    private final SavedRequestMapper savedRequestMapper;


    public ApiTestResponse apiTest(ApiTestRequest apiTestRequest) throws IOException {

        RestClient.RequestBodySpec requestBodySpec = requestBuilder.buildRequest(apiTestRequest);
        ApiTestResponse response =  responseBuilder.execute(requestBodySpec);

        assertionService.executeAssertions(apiTestRequest,response);

        return response;
    }
    public ApiTestResponse apiTest(ApiTestRequest request,
                                   SavedRequest savedRequest) throws IOException {
        return execute(request, savedRequest);
    }

    public ApiTestResponse execute(ApiTestRequest request, SavedRequest savedRequest) throws IOException {
        RestClient.RequestBodySpec requestBodySpec = requestBuilder.buildRequest(request);
        ApiTestResponse response =  responseBuilder.execute(requestBodySpec);

        assertionService.executeAssertions(request,response);


        if(savedRequest != null){
            historyService.save(savedRequest, response);
        }

        return response;
    }
    public ApiTestResponse rerun(UUID historyId) {

        ExecutionHistory history =
                executionHistoryRepository.findById(historyId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Execution history not found"));

        SavedRequest savedRequest = history.getSavedRequest();

        ApiTestRequest request =
                savedRequestMapper.toApiRequest(savedRequest);

        try {

            return execute(request, savedRequest);

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

    }


}