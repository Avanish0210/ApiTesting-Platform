package com.example.ApiTesting.service;

import java.util.UUID;

public interface ReportService {

    byte[] generatePdf(UUID runId);

    byte[] generateExcel(UUID runId);

    byte[] generateCsv(UUID runId);

}
