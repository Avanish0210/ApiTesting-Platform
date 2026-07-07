package com.example.ApiTesting.dto;

import com.example.ApiTesting.entity.BodyType;
import com.example.ApiTesting.entity.RawType;
import lombok.Data;

import java.util.List;

@Data
public class BodyDto {

    private BodyType type;

    // RAW
    private RawType rawType;

    // Raw JSON/XML/Text
    private String raw;

    // Multipart
    private List<FormDataDto> formData;

    // x-www-form-urlencoded
    private List<QueryParamDto> urlEncoded;

    // Binary file path
    private String binaryFilePath;
}
