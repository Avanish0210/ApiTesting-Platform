package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.entity.Environment;
import com.example.ApiTesting.entity.EnvironmentVariable;
import com.example.ApiTesting.repository.EnvironmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnvironmentResolverServiceImpl
        implements EnvironmentResolverService {

    private final EnvironmentRepository repository;

    @Override
    public ApiTestRequest resolve(ApiTestRequest request) {

        Environment environment =
                repository.findByActiveTrue()
                        .orElse(null);

        if (environment == null) {
            return request;
        }

        Map<String, String> variables =
                environment.getVariables()
                        .stream()
                        .filter(EnvironmentVariable::getEnabled)
                        .collect(Collectors.toMap(
                                EnvironmentVariable::getVariableKey,
                                EnvironmentVariable::getVariableValue
                        ));

        resolveUrl(request, variables);
        resolveHeaders(request, variables);
        resolveQueryParameters(request, variables);
        resolveCookies(request, variables);
        resolveAuthentication(request, variables);
        resolveBody(request, variables);

        return request;
    }
    private String replaceVariables(
            String value,
            Map<String, String> variables) {

        if (value == null) {
            return null;
        }

        for (Map.Entry<String, String> entry : variables.entrySet()) {

            value = value.replace(
                    "{{" + entry.getKey() + "}}",
                    entry.getValue());

        }

        return value;
    }
    private void resolveUrl(
            ApiTestRequest request,
            Map<String, String> variables) {

        request.setUrl(
                replaceVariables(
                        request.getUrl(),
                        variables));
    }
    private void resolveHeaders(
            ApiTestRequest request,
            Map<String, String> variables) {

        if (request.getHeaders() == null)
            return;

        request.getHeaders().forEach(header -> {

            header.setKey(
                    replaceVariables(
                            header.getKey(),
                            variables));

            header.setValue(
                    replaceVariables(
                            header.getValue(),
                            variables));

        });

    }
    private void resolveQueryParameters(
            ApiTestRequest request,
            Map<String, String> variables) {

        if (request.getQueryParams() == null)
            return;

        request.getQueryParams().forEach(param -> {

            param.setKey(
                    replaceVariables(
                            param.getKey(),
                            variables));

            param.setValue(
                    replaceVariables(
                            param.getValue(),
                            variables));

        });

    }
    private void resolveCookies(
            ApiTestRequest request,
            Map<String, String> variables) {

        if (request.getCookies() == null)
            return;

        request.getCookies().forEach(cookie -> {

            cookie.setName(
                    replaceVariables(
                            cookie.getName(),
                            variables));

            cookie.setValue(
                    replaceVariables(
                            cookie.getValue(),
                            variables));

        });

    }
    private void resolveAuthentication(
            ApiTestRequest request,
            Map<String, String> variables) {

        if (request.getAuthentication() == null)
            return;

        var auth = request.getAuthentication();

        auth.setUsername(
                replaceVariables(auth.getUsername(), variables));

        auth.setPassword(
                replaceVariables(auth.getPassword(), variables));

        auth.setToken(
                replaceVariables(auth.getToken(), variables));

        auth.setApiKeyHeaderName(
                replaceVariables(auth.getApiKeyHeaderName(), variables));

        auth.setApiKeyValue(
                replaceVariables(auth.getApiKeyValue(), variables));
    }
    private void resolveBody(
            ApiTestRequest request,
            Map<String, String> variables) {

        if (request.getBody() == null)
            return;

        if (request.getBody().getRaw() != null) {

            request.getBody().setRaw(
                    replaceVariables(
                            request.getBody().getRaw(),
                            variables));

        }

        if (request.getBody().getFormData() != null) {

            request.getBody().getFormData().forEach(form -> {

                form.setKey(
                        replaceVariables(
                                form.getKey(),
                                variables));

                form.setValue(
                        replaceVariables(
                                form.getValue(),
                                variables));

            });

        }

    }

}
