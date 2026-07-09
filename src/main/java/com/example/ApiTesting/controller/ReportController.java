package com.example.ApiTesting.controller;

import com.example.ApiTesting.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping(
            value="/runs/{runId}/pdf",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> pdf(
            @PathVariable UUID runId){

        byte[] pdf =
                reportService.generatePdf(runId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report.pdf")
                .body(pdf);

    }

    @GetMapping(
            value = "/runs/{runId}/excel",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> excel(
            @PathVariable UUID runId) throws IOException {

        byte[] file = reportService.generateExcel(runId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report.xlsx")
                .body(file);
    }

    @GetMapping(
            value = "/runs/{runId}/csv",
            produces = "text/csv")
    public ResponseEntity<byte[]> csv(
            @PathVariable UUID runId) {

        byte[] file = reportService.generateCsv(runId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=report.csv")
                .body(file);
    }

}