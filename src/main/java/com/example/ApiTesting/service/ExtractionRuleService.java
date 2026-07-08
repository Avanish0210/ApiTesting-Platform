package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.CreateExtractionRuleRequest;
import com.example.ApiTesting.dto.ExtractionRuleResponse;
import com.example.ApiTesting.dto.UpdateExtractionRuleRequest;

import java.util.List;
import java.util.UUID;

public interface ExtractionRuleService {

    ExtractionRuleResponse create(
            UUID requestId,
            CreateExtractionRuleRequest request);

    ExtractionRuleResponse update(
            UUID ruleId,
            UpdateExtractionRuleRequest request);

    ExtractionRuleResponse findById(UUID ruleId);

    List<ExtractionRuleResponse> findByRequest(UUID requestId);

    void delete(UUID ruleId);

}
