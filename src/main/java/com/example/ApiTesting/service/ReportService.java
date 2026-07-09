package com.example.ApiTesting.service;

import java.io.IOException;
import java.util.UUID;

public interface ReportService {

    byte[] generatePdf(UUID runId);

    byte[] generateExcel(UUID runId) throws IOException;

    byte[] generateCsv(UUID runId);

}
