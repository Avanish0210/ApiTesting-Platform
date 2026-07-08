package com.example.ApiTesting.mapper;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.Environment;
import com.example.ApiTesting.entity.EnvironmentVariable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EnvironmentMapper {

    public Environment toEntity(CreateEnvironmentRequest dto) {

        Environment environment = new Environment();

        environment.setName(dto.getName());
        environment.setDescription(dto.getDescription());

        if (dto.getVariables() != null) {

            List<EnvironmentVariable> variables = dto.getVariables()
                    .stream()
                    .map(variableDto -> {

                        EnvironmentVariable variable =
                                new EnvironmentVariable();

                        variable.setVariableKey(variableDto.getVariableKey());
                        variable.setVariableValue(variableDto.getVariableValue());
                        variable.setEnabled(variableDto.getEnabled());
                        variable.setSecret(variableDto.getSecret());

                        variable.setEnvironment(environment);

                        return variable;

                    })
                    .toList();

            environment.setVariables(new ArrayList<>(variables));
        }

        return environment;
    }

    public EnvironmentResponse toResponse(Environment environment) {

        return EnvironmentResponse.builder()
                .id(environment.getId())
                .name(environment.getName())
                .description(environment.getDescription())
                .active(environment.getActive())
                .createdAt(environment.getCreatedAt())
                .updatedAt(environment.getUpdatedAt())
                .variables(mapVariables(environment))
                .build();
    }

    public void updateEntity(UpdateEnvironmentRequest dto,
                             Environment environment) {

        environment.setName(dto.getName());
        environment.setDescription(dto.getDescription());

        environment.getVariables().clear();

        if (dto.getVariables() != null) {

            dto.getVariables()
                    .forEach(variableDto -> {

                        EnvironmentVariable variable =
                                new EnvironmentVariable();

                        variable.setVariableKey(variableDto.getVariableKey());
                        variable.setVariableValue(variableDto.getVariableValue());
                        variable.setEnabled(variableDto.getEnabled());
                        variable.setSecret(variableDto.getSecret());

                        variable.setEnvironment(environment);

                        environment.getVariables().add(variable);

                    });

        }

    }

    private List<EnvironmentVariableDto> mapVariables(
            Environment environment) {

        if (environment.getVariables() == null) {
            return List.of();
        }

        return environment.getVariables()
                .stream()
                .map(variable -> {

                    EnvironmentVariableDto dto =
                            new EnvironmentVariableDto();

                    dto.setVariableKey(variable.getVariableKey());
                    dto.setVariableValue(variable.getVariableValue());
                    dto.setEnabled(variable.getEnabled());
                    dto.setSecret(variable.getSecret());

                    return dto;

                })
                .toList();
    }

}
