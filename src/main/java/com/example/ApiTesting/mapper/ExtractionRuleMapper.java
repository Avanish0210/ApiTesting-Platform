package com.example.ApiTesting.mapper;

import com.example.ApiTesting.dto.CreateExtractionRuleRequest;
import com.example.ApiTesting.dto.ExtractionRuleResponse;
import com.example.ApiTesting.dto.UpdateExtractionRuleRequest;
import com.example.ApiTesting.entity.ExtractionRule;
import com.example.ApiTesting.entity.SavedRequest;
import org.springframework.stereotype.Component;

@Component
public class ExtractionRuleMapper {

    public ExtractionRule toEntity(
            CreateExtractionRuleRequest dto,
            SavedRequest request) {

        ExtractionRule rule = new ExtractionRule();

        rule.setVariableName(dto.getVariableName());
        rule.setJsonPath(dto.getJsonPath());
        rule.setEnabled(dto.getEnabled());

        rule.setSavedRequest(request);

        return rule;
    }

    public ExtractionRuleResponse toResponse(
            ExtractionRule rule) {

        return ExtractionRuleResponse.builder()
                .id(rule.getId())
                .variableName(rule.getVariableName())
                .jsonPath(rule.getJsonPath())
                .enabled(rule.getEnabled())
                .build();
    }

    public void updateEntity(
            UpdateExtractionRuleRequest dto,
            ExtractionRule rule) {

        rule.setVariableName(dto.getVariableName());
        rule.setJsonPath(dto.getJsonPath());
        rule.setEnabled(dto.getEnabled());
    }

}
