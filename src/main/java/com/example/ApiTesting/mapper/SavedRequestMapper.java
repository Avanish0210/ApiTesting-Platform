package com.example.ApiTesting.mapper;

import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SavedRequestMapper {

    public SavedRequest toEntity(CreateSavedRequestRequest dto,
                                 Collection collection) {

        SavedRequest request = new SavedRequest();

        request.setName(dto.getName());
        request.setHttpMethod(dto.getHttpMethod());
        request.setUrl(dto.getUrl());
        request.setTimeout(dto.getTimeout());
        request.setFollowRedirects(dto.getFollowRedirects());
        request.setPreRequestScript(dto.getPreRequestScript());
        request.setPostResponseScript(dto.getPostResponseScript());

        request.setCollection(collection);

        mapHeaders(dto, request);
        mapQueryParameters(dto, request);
        mapCookies(dto, request);
        mapAuthentication(dto, request);
        mapBody(dto, request);
        mapAssertions(dto, request);

        return request;
    }

    public SavedRequestResponse toResponse(SavedRequest request) {
        return SavedRequestResponse.builder()
                .id(request.getId())
                .name(request.getName())
                .httpMethod(request.getHttpMethod())
                .url(request.getUrl())
                .timeout(request.getTimeout())
                .followRedirects(request.getFollowRedirects())
                .preRequestScript(request.getPreRequestScript())
                .postResponseScript(request.getPostResponseScript())
                .build();
    }

    public ApiTestRequest toApiRequest(SavedRequest request) {

        ApiTestRequest dto = new ApiTestRequest();

        dto.setHttpMethod(request.getHttpMethod());
        dto.setUrl(request.getUrl());
        dto.setTimeout(request.getTimeout());
        dto.setFollowRedirects(request.getFollowRedirects());

        dto.setHeaders(mapHeaders(request));
        dto.setQueryParams(mapQueryParameters(request));
        dto.setCookies(mapCookies(request));
        dto.setAuthentication(mapAuthentication(request));
        dto.setBody(mapBody(request));
        dto.setAssertions(mapAssertions(request));

        return dto;
    }
    private void mapHeaders(CreateSavedRequestRequest dto,
                            SavedRequest request){

        if (dto.getHeaders() == null || dto.getHeaders().isEmpty()) {
            return;
        }

        List<RequestHeader> headers = dto.getHeaders()
                .stream()
                .map(headerDto -> {

                    RequestHeader header = new RequestHeader();

                    header.setKey(headerDto.getKey());
                    header.setValue(headerDto.getValue());
                    header.setEnabled(headerDto.getEnabled());

                    header.setRequest(request);

                    return header;

                })
                .toList();

        request.setHeaders(headers);

    }
    private void mapQueryParameters(CreateSavedRequestRequest dto,
                                    SavedRequest request){

        if (dto.getQueryParameters() == null || dto.getQueryParameters().isEmpty()) {
            return;
        }

        List<QueryParameter> params = dto.getQueryParameters()
                .stream()
                .map(paramDto -> {

                    QueryParameter param = new QueryParameter();

                    param.setKey(paramDto.getKey());
                    param.setValue(paramDto.getValue());
                    param.setEnabled(paramDto.isEnabled());

                    param.setRequest(request);

                    return param;

                })
                .toList();

        request.setQueryParameters(params);

    }
    private void mapCookies(CreateSavedRequestRequest dto,
                            SavedRequest request){

        if (dto.getCookies() == null || dto.getCookies().isEmpty()) {
            return;
        }

        List<RequestCookie> cookies = dto.getCookies()
                .stream()
                .map(cookieDto -> {

                    RequestCookie cookie = new RequestCookie();

                    cookie.setName(cookieDto.getName());
                    cookie.setValue(cookieDto.getValue());
                    cookie.setEnabled(cookieDto.isEnabled());

                    cookie.setRequest(request);

                    return cookie;

                })
                .toList();

        request.setCookies(cookies);

    }
    private void mapAuthentication(CreateSavedRequestRequest dto,
                                   SavedRequest request){
        if (dto.getAuthentication() == null) {
            return;
        }

        AuthDto authDto = dto.getAuthentication();

        RequestAuthentication auth = new RequestAuthentication();

        auth.setAuthType(authDto.getType());
        auth.setUsername(authDto.getUsername());
        auth.setPassword(authDto.getPassword());

        auth.setBearerToken(authDto.getToken());

        auth.setApiKeyName(authDto.getApiKeyHeaderName());
        auth.setApiKeyValue(authDto.getApiKeyValue());

        auth.setRequest(request);

        request.setAuthentication(auth);

    }

    private void mapAssertions(CreateSavedRequestRequest dto,
                               SavedRequest request){

        if (dto.getAssertions() == null || dto.getAssertions().isEmpty()) {
            return;
        }

        List<RequestAssertion> assertions = dto.getAssertions()
                .stream()
                .map(assertionDto -> {

                    RequestAssertion assertion = new RequestAssertion();

                    assertion.setType(assertionDto.getType());
                    assertion.setExpression(assertionDto.getExpression());
                    assertion.setExpectedValue(assertionDto.getExpectedValue());
                    assertion.setOperator(assertionDto.getOperator());

                    assertion.setRequest(request);

                    return assertion;

                })
                .toList();

        request.setAssertions(assertions);

    }

    private void mapBody(CreateSavedRequestRequest dto,
                         SavedRequest request){

        if (request.getBody() != null) {

            BodyDto bodyDto = new BodyDto();

            bodyDto.setType(request.getBody().getBodyType());
            bodyDto.setRawType(request.getBody().getRawType());
            bodyDto.setRaw(request.getBody().getRawBody());
            bodyDto.setBinaryFilePath(request.getBody().getBinaryFilePath());

            if (request.getBody().getFormData() != null) {

                bodyDto.setFormData(
                        request.getBody()
                                .getFormData()
                                .stream()
                                .map(form -> {

                                    FormDataDto dtoForm = new FormDataDto();

                                    dtoForm.setKey(form.getKey());
                                    dtoForm.setValue(form.getValue());
                                    dtoForm.setFile(form.getFile());
                                    dtoForm.setFilePath(form.getFilePath());
                                    dtoForm.setEnabled(form.getEnabled());

                                    return dtoForm;

                                })
                                .toList()
                );
            }
            dto.setBody(bodyDto);
        }
    }

    private List<HeaderDto> mapHeaders(SavedRequest request) {

        if (request.getHeaders() == null || request.getHeaders().isEmpty()) {
            return List.of();
        }

        return request.getHeaders()
                .stream()
                .map(header -> {

                    HeaderDto dto = new HeaderDto();

                    dto.setKey(header.getKey());
                    dto.setValue(header.getValue());
                    dto.setEnabled(header.getEnabled());

                    return dto;

                })
                .toList();
    }
    private List<QueryParamDto> mapQueryParameters(SavedRequest request) {

        if (request.getQueryParameters() == null
                || request.getQueryParameters().isEmpty()) {

            return List.of();
        }

        return request.getQueryParameters()
                .stream()
                .map(parameter -> {

                    QueryParamDto dto = new QueryParamDto();

                    dto.setKey(parameter.getKey());
                    dto.setValue(parameter.getValue());
                    dto.setEnabled(parameter.getEnabled());

                    return dto;

                })
                .toList();
    }
    private List<CookieDto> mapCookies(SavedRequest request) {

        if (request.getCookies() == null
                || request.getCookies().isEmpty()) {

            return List.of();
        }

        return request.getCookies()
                .stream()
                .map(cookie -> {

                    CookieDto dto = new CookieDto();

                    dto.setName(cookie.getName());
                    dto.setValue(cookie.getValue());
                    dto.setEnabled(cookie.getEnabled());

                    return dto;

                })
                .toList();
    }
    private AuthDto mapAuthentication(SavedRequest request) {

        if (request.getAuthentication() == null) {
            return null;
        }

        RequestAuthentication auth = request.getAuthentication();

        AuthDto dto = new AuthDto();

        dto.setType(auth.getAuthType());

        dto.setUsername(auth.getUsername());
        dto.setPassword(auth.getPassword());

        dto.setToken(auth.getBearerToken());

        dto.setApiKeyHeaderName(auth.getApiKeyName());
        dto.setApiKeyValue(auth.getApiKeyValue());

        dto.setApiKeyLocation(auth.getApiKeyLocation());

        return dto;
    }
    private BodyDto mapBody(SavedRequest request) {

        if (request.getBody() == null) {
            return null;
        }

        RequestBody body = request.getBody();

        BodyDto dto = new BodyDto();

        dto.setType(body.getBodyType());
        dto.setRawType(body.getRawType());
        dto.setRaw(body.getRawBody());
        dto.setBinaryFilePath(body.getBinaryFilePath());

        if (body.getFormData() != null && !body.getFormData().isEmpty()) {

            dto.setFormData(

                    body.getFormData()
                            .stream()
                            .map(form -> {

                                FormDataDto formDto =
                                        new FormDataDto();

                                formDto.setKey(form.getKey());
                                formDto.setValue(form.getValue());

                                formDto.setFile(form.getFile());

                                formDto.setFilePath(form.getFilePath());

                                formDto.setEnabled(form.getEnabled());

                                return formDto;

                            })
                            .toList()

            );
        }

        return dto;
    }
    private List<AssertionDto> mapAssertions(SavedRequest request) {

        if (request.getAssertions() == null
                || request.getAssertions().isEmpty()) {

            return List.of();
        }

        return request.getAssertions()
                .stream()
                .map(assertion -> {

                    AssertionDto dto = new AssertionDto();

                    dto.setType(assertion.getType());

                    dto.setExpression(assertion.getExpression());

                    dto.setExpectedValue(assertion.getExpectedValue());

                    dto.setOperator(assertion.getOperator());

                    return dto;

                })
                .toList();
    }

}
