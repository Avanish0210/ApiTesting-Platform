package com.example.ApiTesting.service;

import org.apache.poi.ss.usermodel.*;
import com.example.ApiTesting.entity.CollectionRun;
import com.example.ApiTesting.entity.CollectionRunItem;
import com.example.ApiTesting.exception.ResourceNotFoundException;
import com.example.ApiTesting.repository.CollectionRunItemRepository;
import com.example.ApiTesting.repository.CollectionRunRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public byte[] generateExcel(UUID runId) throws IOException {
        CollectionRun run =
                runRepository.findById(runId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Run not found"));

        List<CollectionRunItem> items =
                itemRepository.findByRunIdOrderByExecutionOrder(runId);

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Execution Report");


// ----------------------
// Fonts
// ----------------------

        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short)18);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());

        Font boldFont = workbook.createFont();
        boldFont.setBold(true);

// ----------------------
// Title Style
// ----------------------

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

// ----------------------
// Label Style
// ----------------------

        CellStyle labelStyle = workbook.createCellStyle();
        labelStyle.setFont(boldFont);

// ----------------------
// Header Style
// ----------------------

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

// ----------------------
// PASS Style
// ----------------------

        CellStyle passStyle = workbook.createCellStyle();
        passStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        passStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

// ----------------------
// FAIL Style
// ----------------------

        CellStyle failStyle = workbook.createCellStyle();
        failStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        failStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);


        int rowNum = 0;

        Row titleRow = sheet.createRow(rowNum++);

        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("API TEST EXECUTION REPORT");
        titleCell.setCellStyle(titleStyle);

        sheet.addMergedRegion(new CellRangeAddress(
                0,
                0,
                0,
                4
        ));

        rowNum++;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");

        Row row;

        row = sheet.createRow(rowNum++);
        Cell c = row.createCell(0);
        c.setCellValue("Collection");
        c.setCellStyle(labelStyle);
        row.createCell(1).setCellValue(run.getCollection().getName());

        row = sheet.createRow(rowNum++);
        c = row.createCell(0);
        c.setCellValue("Status");
        c.setCellStyle(labelStyle);
        row.createCell(1).setCellValue(run.getStatus().name());

        row = sheet.createRow(rowNum++);
        c = row.createCell(0);
        c.setCellValue("Started");
        c.setCellStyle(labelStyle);
        row.createCell(1).setCellValue(run.getStartedAt().format(formatter));

        row = sheet.createRow(rowNum++);
        c = row.createCell(0);
        c.setCellValue("Finished");
        c.setCellStyle(labelStyle);

        if (run.getFinishedAt() != null) {
            row.createCell(1).setCellValue(run.getFinishedAt().format(formatter));
        }

        row = sheet.createRow(rowNum++);
        c = row.createCell(0);
        c.setCellValue("Total Requests");
        c.setCellStyle(labelStyle);
        row.createCell(1).setCellValue(run.getTotalRequests());

        row = sheet.createRow(rowNum++);
        c = row.createCell(0);
        c.setCellValue("Passed");
        c.setCellStyle(labelStyle);
        row.createCell(1).setCellValue(run.getSuccessfulRequests());

        row = sheet.createRow(rowNum++);
        c = row.createCell(0);
        c.setCellValue("Failed");
        c.setCellStyle(labelStyle);
        row.createCell(1).setCellValue(run.getFailedRequests());

        row = sheet.createRow(rowNum++);
        c = row.createCell(0);
        c.setCellValue("Total Time");
        c.setCellStyle(labelStyle);
        row.createCell(1).setCellValue(run.getTotalExecutionTime() + " ms");

        rowNum++;

        Row header = sheet.createRow(rowNum++);

        String[] headers = {
                "Order",
                "Request",
                "Status",
                "HTTP",
                "Response Time (ms)"
        };

        for(int i=0;i<headers.length;i++){

            Cell cell = header.createCell(i);

            cell.setCellValue(headers[i]);

            cell.setCellStyle(headerStyle);

        }

        for (CollectionRunItem item : items) {

            Row data = sheet.createRow(rowNum++);

            data.createCell(0).setCellValue(item.getExecutionOrder());

            data.createCell(1).setCellValue(item.getRequest().getName());

            Cell statusCell = data.createCell(2);

            boolean pass = item.getSuccess();

            statusCell.setCellValue(pass ? "PASS" : "FAIL");
            statusCell.setCellStyle(pass ? passStyle : failStyle);

            data.createCell(3).setCellValue(item.getStatusCode());

            data.createCell(4).setCellValue(item.getResponseTime());
        }

        sheet.setAutoFilter(
                new CellRangeAddress(
                        header.getRowNum(),
                        header.getRowNum(),
                        0,
                        4
                )
        );
        sheet.createFreezePane(0, header.getRowNum() + 1);

        rowNum += 2;

        Row summary = sheet.createRow(rowNum++);
        summary.createCell(0).setCellValue("Execution Summary");
        summary.getCell(0).setCellStyle(labelStyle);

        double successRate = run.getTotalRequests() == 0
                ? 0
                : (run.getSuccessfulRequests() * 100.0) / run.getTotalRequests();

        summary = sheet.createRow(rowNum++);
        summary.createCell(0).setCellValue("Success Rate");
        summary.createCell(1).setCellValue(String.format("%.2f%%", successRate));

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }
        ByteArrayOutputStream output =
                new ByteArrayOutputStream();

        workbook.write(output);

        workbook.close();

        return output.toByteArray();
    }

    @Override
    public byte[] generateCsv(UUID runId) {

        CollectionRun run = runRepository.findById(runId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Run not found"));

        List<CollectionRunItem> items =
                itemRepository.findByRunIdOrderByExecutionOrder(runId);

        StringBuilder csv = new StringBuilder();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
        long avg = items.stream()
                .mapToLong(CollectionRunItem::getResponseTime)
                .sum();

        avg = items.isEmpty() ? 0 : avg / items.size();
        csv.append("Generated On,")
                .append(LocalDateTime.now().format(formatter))
                .append("\n");

        csv.append("API TEST EXECUTION REPORT\n\n");

        csv.append("Collection,")
                .append(run.getCollection().getName())
                .append("\n");

        csv.append("Status,")
                .append(run.getStatus())
                .append("\n");

        csv.append("Started,")
                .append(run.getStartedAt())
                .append("\n");

        csv.append("Finished,")
                .append(run.getFinishedAt())
                .append("\n");

        csv.append("Total Requests,")
                .append(run.getTotalRequests())
                .append("\n");

        csv.append("Passed,")
                .append(run.getSuccessfulRequests())
                .append("\n");

        csv.append("Failed,")
                .append(run.getFailedRequests())
                .append("\n");

        csv.append("Total Time,")
                .append(run.getTotalExecutionTime())
                .append(" ms\n");

        double successRate =
                run.getTotalRequests() == 0
                        ? 0
                        : (run.getSuccessfulRequests() * 100.0)
                        / run.getTotalRequests();

        csv.append("Success Rate,")
                .append(String.format("%.2f%%", successRate))
                .append("\n\n");

        csv.append("Order,Request,Status,HTTP Status,Response Time(ms)\n");

        for (CollectionRunItem item : items) {

            csv.append(item.getExecutionOrder()).append(",");
            csv.append(item.getRequest().getName()).append(",");
            csv.append(item.getSuccess() ? "PASS" : "FAIL").append(",");
            csv.append(item.getStatusCode()).append(",");
            csv.append(item.getResponseTime()).append("\n");
        }

        return csv.toString().getBytes(StandardCharsets.UTF_8);
    }



}
