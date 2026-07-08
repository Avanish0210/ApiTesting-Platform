package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestResponse;
import com.example.ApiTesting.entity.Environment;
import com.example.ApiTesting.entity.EnvironmentVariable;
import com.example.ApiTesting.entity.ExtractionRule;
import com.example.ApiTesting.entity.SavedRequest;
import com.example.ApiTesting.repository.EnvironmentRepository;
import com.example.ApiTesting.repository.EnvironmentVariableRepository;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VariableExtractionServiceImpl
        implements VariableExtractionService {

    private final EnvironmentRepository environmentRepository;
    private final EnvironmentVariableRepository variableRepository;

    @Override
    public void extract(
            SavedRequest savedRequest,
            ApiTestResponse response) {

        if (response.getResponseBody() == null ||
                response.getResponseBody().isBlank()) {

            return;

        }
        Environment environment =
                environmentRepository
                        .findByActiveTrue()
                        .orElse(null);

        if (environment == null) {
            return;
        }
        for (ExtractionRule rule : savedRequest.getExtractionRules()) {

            if (!Boolean.TRUE.equals(rule.getEnabled())) {
                continue;
            }

            Object value;

            try {

                value = JsonPath.read(
                        response.getResponseBody(),
                        rule.getJsonPath());

            } catch (Exception ex) {
                continue;
            }

            if (value == null) {
                continue;
            }

            EnvironmentVariable variable =
                    environment.getVariables()
                            .stream()
                            .filter(v -> v.getVariableKey()
                                    .equals(rule.getVariableName()))
                            .findFirst()
                            .orElse(null);

            if (variable == null) {

                variable = new EnvironmentVariable();
                variable.setEnvironment(environment);
                variable.setVariableKey(rule.getVariableName());
                variable.setEnabled(true);

                environment.getVariables().add(variable);
            }

            variable.setVariableValue(String.valueOf(value));

            variableRepository.save(variable);
        }
    }
}
