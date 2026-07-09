package com.example.ApiTesting.service;

import com.example.ApiTesting.entity.CollectionRun;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.repository.CollectionRunItemRepository;
import com.example.ApiTesting.repository.CollectionRunRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final CollectionRunRepository runRepository;
    private final EmailTemplateService emailTemplateService;
    private final CollectionRunItemRepository itemRepository;

    @Override
    public byte[] generatePdf(UUID runId) {

        CollectionRun run = runRepository.findById(runId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Run not found"));

        String html =
                emailTemplateService.buildExecutionReport(run);

        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            PdfRendererBuilder builder = new PdfRendererBuilder();

            builder.withHtmlContent(html, null);
            builder.toStream(output);
            builder.run();

            return output.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }

    }

    @Override
    public byte[] generateExcel(UUID runId) {
        return new byte[0];
    }

    @Override
    public byte[] generateCsv(UUID runId) {
        return new byte[0];
    }



}
