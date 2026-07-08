package com.example.ApiTesting.service;

import com.example.ApiTesting.dto.DatasetRow;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CsvDatasetParser implements DatasetParser {
    @Override
    public List<DatasetRow> parse(byte[] file)
            throws IOException {

        List<DatasetRow> rows = new ArrayList<>();

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        new ByteArrayInputStream(file)))
        ) {

            CSVParser parser =
                    CSVFormat.DEFAULT
                            .builder()
                            .setHeader()
                            .setSkipHeaderRecord(true)
                            .build()
                            .parse(reader);

            for (CSVRecord record : parser) {

                Map<String, String> values = new HashMap<>();

                parser.getHeaderMap()
                        .keySet()
                        .forEach(header ->

                                values.put(
                                        header,
                                        record.get(header))
                        );

                rows.add(
                        DatasetRow.builder()
                                .values(values)
                                .build()
                );
            }
        }

        return rows;
    }
}
