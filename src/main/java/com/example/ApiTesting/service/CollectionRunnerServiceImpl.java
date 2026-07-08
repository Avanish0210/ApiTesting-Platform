package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.dto.CollectionRunResponse;
import com.example.ApiTesting.entity.*;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.SavedRequestMapper;
import com.example.ApiTesting.repository.CollectionRepository;
import com.example.ApiTesting.repository.CollectionRunRepository;
import com.example.ApiTesting.repository.SavedRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CollectionRunnerServiceImpl
        implements CollectionRunnerService {

    private final CollectionRepository collectionRepository;
    private final SavedRequestRepository savedRequestRepository;
    private final CollectionRunRepository runRepository;
    private final ApiService apiService;
    private final SavedRequestMapper mapper;
    private final NotificationService notificationService;

    @Override
    public CollectionRunResponse run(UUID collectionId) {

        Collection collection = collectionRepository.findById(collectionId).orElseThrow(() -> new ResourceNotFoundException("Collection not found"));
        List<SavedRequest> requests = savedRequestRepository.findByCollectionId(collectionId);
        requests.sort(Comparator.comparing(SavedRequest::getName));//later we have to add order index in place of geName
        CollectionRun run =
                CollectionRun.builder()
                        .collection(collection)
                        .startedAt(LocalDateTime.now())
                        .status(RunStatus.RUNNING)
                        .totalRequests(requests.size())
                        .successfulRequests(0)
                        .failedRequests(0)
                        .build();

        long totalExecutionTime = 0;

        int success = 0;

        int failed = 0;
        int order = 1;
        for (SavedRequest request : requests) {

            ApiTestRequest apiRequest = mapper.toApiRequest(request);

            try {

                ApiTestResponse response =
                        apiService.execute(apiRequest, request);

                success++;
                totalExecutionTime += response.getResponseTime();

            } catch (Exception ex) {

                failed++;

            }
        }


        runRepository.save(run);

        run.setFinishedAt(LocalDateTime.now());

        run.setSuccessfulRequests(success);

        run.setFailedRequests(failed);

        run.setTotalExecutionTime(totalExecutionTime);

        run.setStatus(
                failed == 0
                        ? RunStatus.COMPLETED
                        : RunStatus.FAILED
        );

        runRepository.save(run);
        notificationService.notifyCollectionRun(run);
        return toResponse(run);
    }
    @Override
    @Transactional
    public List<CollectionRunResponse> history(UUID collectionId) {

        return runRepository
                .findByCollectionIdOrderByStartedAtDesc(collectionId)
                .stream()
                .map(this::toResponse)
                .toList();

    }
    private CollectionRunResponse toResponse(
            CollectionRun run) {

        return CollectionRunResponse.builder()
                .id(run.getId())
                .startedAt(run.getStartedAt())
                .finishedAt(run.getFinishedAt())
                .status(run.getStatus())
                .totalRequests(run.getTotalRequests())
                .successfulRequests(run.getSuccessfulRequests())
                .failedRequests(run.getFailedRequests())
                .totalExecutionTime(run.getTotalExecutionTime())
                .build();

    }

}
