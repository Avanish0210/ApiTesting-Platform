package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.DatasetRow;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvDatasetParserTest {

    @Test
    void shouldParseCsv() throws Exception {

        String csv = """
                name,email,age
                John,john@test.com,25
                Alice,alice@test.com,30
                """;

        CsvDatasetParser parser = new CsvDatasetParser();

        List<DatasetRow> rows =
                parser.parse(csv.getBytes());

        assertEquals(2, rows.size());

        assertEquals("John",
                rows.get(0).getValues().get("name"));

        assertEquals("john@test.com",
                rows.get(0).getValues().get("email"));

        assertEquals("25",
                rows.get(0).getValues().get("age"));

        assertEquals("Alice",
                rows.get(1).getValues().get("name"));
    }
}
