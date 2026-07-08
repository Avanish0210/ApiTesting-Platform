package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CreateExtractionRuleRequest;
import com.example.ApiTesting.dto.ExtractionRuleResponse;
import com.example.ApiTesting.dto.UpdateExtractionRuleRequest;
import com.example.ApiTesting.entity.ExtractionRule;
import com.example.ApiTesting.entity.SavedRequest;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.mapper.ExtractionRuleMapper;
import com.example.ApiTesting.repository.ExtractionRuleRepository;
import com.example.ApiTesting.repository.SavedRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ExtractionRuleServiceImpl
        implements ExtractionRuleService {

    private final ExtractionRuleRepository repository;
    private final SavedRequestRepository savedRequestRepository;
    private final ExtractionRuleMapper mapper;

    @Override
    public ExtractionRuleResponse create(
            UUID requestId,
            CreateExtractionRuleRequest request) {

        SavedRequest savedRequest =
                savedRequestRepository.findById(requestId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Saved request not found"));

        ExtractionRule rule =
                mapper.toEntity(request, savedRequest);

        repository.save(rule);

        return mapper.toResponse(rule);
    }

    @Override
    public ExtractionRuleResponse update(
            UUID ruleId,
            UpdateExtractionRuleRequest request) {

        ExtractionRule rule =
                repository.findById(ruleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Extraction rule not found"));

        mapper.updateEntity(request, rule);

        repository.save(rule);

        return mapper.toResponse(rule);
    }

    @Override
    public ExtractionRuleResponse findById(UUID ruleId) {

        ExtractionRule rule =
                repository.findById(ruleId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Extraction rule not found"));

        return mapper.toResponse(rule);
    }

    @Override
    public List<ExtractionRuleResponse> findByRequest(UUID requestId) {

        return repository.findBySavedRequestId(requestId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(UUID ruleId) {

        if (!repository.existsById(ruleId)) {
            throw new ResourceNotFoundException(
                    "Extraction rule not found");
        }

        repository.deleteById(ruleId);
    }
}
