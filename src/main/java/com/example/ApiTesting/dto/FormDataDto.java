package com.example.ApiTesting.dto;

import lombok.Data;

@Data
public class FormDataDto {

    private String key;

    private String value;

    // true if this part is a file
    private boolean file;

    // path of uploaded file
    private String filePath;

    private boolean enabled = true;
}
