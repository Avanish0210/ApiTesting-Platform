package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.ApiTestRequest;
import com.example.ApiTesting.utility.DynamicVariableGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class DynamicVariableServiceImpl implements DynamicVariableService {
    private final DynamicVariableGenerator generator;
    private static final Pattern PATTERN = Pattern.compile("\\{\\{\\$(.*?)}}");

    @Override
    public ApiTestRequest resolve(ApiTestRequest request) {

        request.setUrl(
                resolveValue(request.getUrl()));

        if (request.getHeaders() != null) {

            request.getHeaders().forEach(header -> {

                header.setValue(
                        resolveValue(header.getValue()));

            });

        }

        if (request.getQueryParams() != null) {

            request.getQueryParams().forEach(param -> {

                param.setValue(
                        resolveValue(param.getValue()));

            });

        }

        if (request.getCookies() != null) {

            request.getCookies().forEach(cookie -> {

                cookie.setValue(
                        resolveValue(cookie.getValue()));

            });

        }

        if (request.getBody() != null &&
                request.getBody().getRaw() != null) {

            request.getBody().setRaw(
                    resolveValue(
                            request.getBody().getRaw()));

        }

        return request;
    }

    private String resolveValue(String value) {

        if (value == null)
            return null;

        Matcher matcher =
                PATTERN.matcher(value);

        StringBuffer buffer =
                new StringBuffer();

        while (matcher.find()) {

            String variable =
                    matcher.group(1);

            matcher.appendReplacement(
                    buffer,
                    getReplacement(variable));
        }
        matcher.appendTail(buffer);

        return buffer.toString();
    }
    private String getReplacement(String variable) {

        return switch (variable) {

            case "uuid" ->
                    generator.uuid();

            case "guid" ->
                    generator.guid();

            case "timestamp" ->
                    generator.timestamp();

            case "isoTimestamp" ->
                    generator.isoTimestamp();

            case "randomInt" ->
                    generator.randomInt();

            case "randomString" ->
                    generator.randomString();

            case "randomEmail" ->
                    generator.randomEmail();

            case "randomBoolean" ->
                    generator.randomBoolean();

            case "randomFirstName" ->
                    generator.randomFirstName();

            case "randomLastName" ->
                    generator.randomLastName();

            case "randomPhone" ->
                    generator.randomPhone();

            default ->
                    "";
        };

    }

}
