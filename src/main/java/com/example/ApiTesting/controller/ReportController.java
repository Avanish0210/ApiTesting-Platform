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

}