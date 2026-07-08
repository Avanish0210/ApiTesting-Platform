package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataDrivenVariableResolverImpl
        implements DataDrivenVariableResolver {

    @Override
    public ApiTestRequest resolve(
            ApiTestRequest request,
            DatasetRow row) {

        request.setUrl(
                replace(
                        request.getUrl(),
                        row));

        // headers

        if (request.getHeaders() != null) {

            request.getHeaders()
                    .forEach(header ->
                            header.setValue(
                                    replace(
                                            header.getValue(),
                                            row)));
        }

        // query params

        if (request.getQueryParams() != null) {

            request.getQueryParams()
                    .forEach(param ->
                            param.setValue(
                                    replace(
                                            param.getValue(),
                                            row)));
        }

        // cookies

        if (request.getCookies() != null) {

            request.getCookies()
                    .forEach(cookie ->
                            cookie.setValue(
                                    replace(
                                            cookie.getValue(),
                                            row)));
        }

        // body

        if (request.getBody() != null) {

            if (request.getBody().getRaw() != null) {

                request.getBody().setRaw(
                        replace(
                                request.getBody().getRaw(),
                                row));
            }

            if (request.getBody().getFormData() != null) {

                request.getBody()
                        .getFormData()
                        .forEach(form ->

                                form.setValue(
                                        replace(
                                                form.getValue(),
                                                row))
                        );
            }
        }

        return request;
    }

    private String replace(
            String text,
            DatasetRow row) {

        if (text == null)
            return null;

        for (String key : row.getValues().keySet()) {

            text = text.replace(
                    "{{" + key + "}}",
                    row.getValues().get(key));
        }

        return text;
    }

}
