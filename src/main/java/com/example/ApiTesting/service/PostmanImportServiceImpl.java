package com.example.ApiTesting.service;


import com.example.ApiTesting.dto.*;
import com.example.ApiTesting.entity.*;
import com.example.ApiTesting.repository.CollectionRepository;
import com.example.ApiTesting.repository.EnvironmentRepository;
import com.example.ApiTesting.repository.SavedRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostmanImportServiceImpl implements PostmanImportService {

    private final ObjectMapper objectMapper;

    private final CollectionRepository collectionRepository;

    private final SavedRequestRepository savedRequestRepository;
    private final EnvironmentRepository environmentRepository;

    @Override
    public Collection importCollection(MultipartFile file) {

        try {

            PostmanCollectionDto dto =
                    objectMapper.readValue(
                            file.getInputStream(),
                            PostmanCollectionDto.class);

            // Create Collection
            Collection collection = Collection.builder()
                    .name(dto.getInfo().getName())
                    .description(dto.getInfo().getDescription())
                    .build();

            collection = collectionRepository.save(collection);

            // ==========================
            // IMPORT VARIABLES HERE
            // ==========================

            if (dto.getVariable() != null) {

                Environment environment = new Environment();

                environment.setName(collection.getName() + " Environment");

                List<EnvironmentVariable> variables = new ArrayList<>();

                for (PostmanVariableDto variable : dto.getVariable()) {

                    EnvironmentVariable env = new EnvironmentVariable();

                    env.setVariableKey(variable.getKey());
                    env.setVariableValue(variable.getValue());

                    variables.add(env);
                }

                environment.setVariables(variables);

                environmentRepository.save(environment);

                collection.setEnvironment(environment);

                collectionRepository.save(collection);
            }

            // ==========================
            // IMPORT REQUESTS
            // ==========================

            if (dto.getItem() != null) {

                for (PostmanItemDto item : dto.getItem()) {

                    importItem(item, collection);

                }

            }

            return collection;

        } catch (Exception e) {

            throw new RuntimeException("Failed to import Postman collection", e);

        }
    }

    private void importItem(
            PostmanItemDto item,
            Collection collection) {

        // Folder
        if (item.getItem() != null && !item.getItem().isEmpty()) {

            for (PostmanItemDto child : item.getItem()) {

                importItem(child, collection);

            }

            return;
        }

        if (item.getRequest() == null) {
            return;
        }

        SavedRequest request = SavedRequest.builder()
                .collection(collection)
                .name(item.getName())
                .httpMethod(HttpMethod.valueOf(item.getRequest().getMethod()))
                .url(item.getRequest().getUrl().getRaw())
                .build();

        if (item.getRequest().getHeader() != null) {

            List<RequestHeader> headers = new ArrayList<>();

            for (PostmanHeaderDto h : item.getRequest().getHeader()) {

                RequestHeader dto = new RequestHeader();
                dto.setKey(h.getKey());
                dto.setValue(h.getValue());


                headers.add(dto);
            }

            request.setHeaders(headers);
        }

        if (item.getRequest().getUrl() != null &&
                item.getRequest().getUrl().getQuery() != null) {

            List<QueryParameter> params = new ArrayList<>();

            for (PostmanQueryParamDto q :
                    item.getRequest().getUrl().getQuery()) {

                QueryParameter dto = new QueryParameter();

                dto.setKey(q.getKey());
                dto.setValue(q.getValue());

                params.add(dto);
            }

            request.setQueryParameters(params);
        }
        if (item.getRequest().getBody() != null) {

            RequestBody body = new RequestBody();

            body.setBodyType(BodyType.RAW);

            body.setRawBody(
                    item.getRequest()
                            .getBody()
                            .getRaw());

            request.setBody(body);
        }

        if (item.getRequest().getAuth() != null) {

            PostmanAuthDto auth = item.getRequest().getAuth();

            if ("bearer".equalsIgnoreCase(auth.getType())) {

                String token = auth.getBearer()
                        .stream()
                        .filter(a -> "token".equals(a.getKey()))
                        .map(PostmanAuthAttributeDto::getValue)
                        .findFirst()
                        .orElse("");
                RequestAuthentication authType = new RequestAuthentication();
                authType.setAuthType(AuthType.BEARER);
                authType.setBearerToken(token);
                request.setAuthentication(authType);
            }

            if ("basic".equalsIgnoreCase(auth.getType())) {

                String username = "";
                String password = "";

                for (PostmanAuthAttributeDto a : auth.getBasic()) {

                    if ("username".equals(a.getKey()))
                        username = a.getValue();

                    if ("password".equals(a.getKey()))
                        password = a.getValue();
                }
                RequestAuthentication authType = new RequestAuthentication();
                authType.setAuthType(AuthType.BEARER);
                authType.setUsername(username);
                authType.setPassword(password);

                request.setAuthentication(authType);
            }
            if ("apikey".equalsIgnoreCase(auth.getType())) {

                String key = "";
                String value = "";

                for (PostmanAuthAttributeDto a : auth.getApikey()) {

                    if ("key".equals(a.getKey()))
                        key = a.getValue();

                    if ("value".equals(a.getKey()))
                        value = a.getValue();
                }

                RequestAuthentication authType = new RequestAuthentication();
                authType.setAuthType(AuthType.API_KEY);
                authType.setApiKeyName(key);
                authType.setApiKeyValue(value);

                request.setAuthentication(authType);
            }

        }


        savedRequestRepository.save(request);

    }

}
