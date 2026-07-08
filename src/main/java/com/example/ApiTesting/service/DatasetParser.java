package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.DatasetRow;

import java.io.IOException;
import java.util.List;

public interface DatasetParser {

    List<DatasetRow> parse(byte[] file)
            throws IOException;

}
