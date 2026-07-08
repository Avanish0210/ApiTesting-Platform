package com.example.ApiTesting.dto;


import lombok.experimental.UtilityClass;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            return null;
        }

    }

    public static <T> T fromJson(String json, Class<T> clazz) {

        try {
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }

    }

    public static <T> List<T> fromJsonList(
            String json,
            Class<T> clazz) {

        try {

            JavaType type = mapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);

            return mapper.readValue(json, type);

        } catch (Exception e) {

            return Collections.emptyList();

        }
    }

}
