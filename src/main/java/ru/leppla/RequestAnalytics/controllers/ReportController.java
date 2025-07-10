package ru.leppla.RequestAnalytics.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.leppla.RequestAnalytics.dto.ReportRequestDTO;
import ru.leppla.RequestAnalytics.entity.Report;
import ru.leppla.RequestAnalytics.entity.RequestSummaryView;
import ru.leppla.RequestAnalytics.service.ReportService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<String> createReport(@RequestBody ReportRequestDTO dto) {
        reportService.createReport(dto);
        return ResponseEntity.ok("Создание отчета начато");
    }

    @GetMapping
    public ResponseEntity<?> getReports() {
        return ResponseEntity.ok(reportService.getReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportResult(@PathVariable Long id) {
        Report report = reportService.getReportResult(id);
        if ("COMPLETED".equals(report.getStatus())) {

            try {
                return ResponseEntity.ok().body(Files.readAllBytes(Paths.get(report.getFilePath())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Отчет еще не завершен.");
    }

    @GetMapping("/{id}/paged")
    public ResponseEntity<?> getReportResultPaged(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RequestSummaryView> resultPage = reportService.getReportPaged(id, PageRequest.of(page, size));
        return ResponseEntity.ok(resultPage);
    }

}
