package ru.leppla.RequestAnalytics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.leppla.RequestAnalytics.dto.ReportRequestDTO;
import ru.leppla.RequestAnalytics.dto.RequestSummaryFilterDTO;
import ru.leppla.RequestAnalytics.entity.Report;
import ru.leppla.RequestAnalytics.entity.ReportStatus;
import ru.leppla.RequestAnalytics.entity.RequestSummaryView;
import ru.leppla.RequestAnalytics.repository.ReportRepository;
import ru.leppla.RequestAnalytics.repository.RequestSummaryViewRepository;
import ru.leppla.RequestAnalytics.specification.RequestSummarySpecification;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final RequestSummaryViewRepository requestSummaryRepo;
    private final RequestSummarySpecification specBuilder;

    private static final Logger logger = LoggerFactory.getLogger(ReportService.class);


    @Autowired
    public ReportService(ReportRepository reportRepository, RequestSummaryViewRepository requestSummaryRepo,
                         RequestSummarySpecification specBuilder) {
        this.reportRepository = reportRepository;
        this.requestSummaryRepo = requestSummaryRepo;
        this.specBuilder = specBuilder;
    }

    @Transactional
    public void createReport(ReportRequestDTO dto) {
        Report report = new Report();
        report.setHost(dto.getHost());
        report.setPath(dto.getPath());
        report.setRequestFrom(dto.getRequestFrom());
        report.setRequestTo(dto.getRequestTo());
        report.setMinHeaders(dto.getMinHeaders());
        report.setMinParams(dto.getMinParams());
        report.setStatus(ReportStatus.PENDING);
        report.setCreatedAt(LocalDateTime.now());

        reportRepository.save(report);

        generateReportAsync(report.getId());
    }

    @Async
    public void generateReportAsync(Long reportId) {
        Report report = reportRepository.findById(reportId).orElseThrow();

        if (report.getStatus() == ReportStatus.COMPLETED) return;

        try {
            List<String> reportData = generateReportData(report);
            String filePath = generateCsvFile(reportData);

            report.setFilePath(filePath);
            report.setStatus(ReportStatus.COMPLETED);
            reportRepository.save(report);

        } catch (Exception e) {
            report.setStatus(ReportStatus.FAILED);
            reportRepository.save(report);
            e.printStackTrace();
        }
    }

    private List<String> generateReportData(Report report) {
        RequestSummaryFilterDTO filterDTO = new RequestSummaryFilterDTO();
        filterDTO.setHost(report.getHost());
        filterDTO.setPath(report.getPath());
        filterDTO.setFrom(report.getRequestFrom());
        filterDTO.setTo(report.getRequestTo());
        filterDTO.setMinHeaders(report.getMinHeaders());
        filterDTO.setMinParams(report.getMinParams());

        List<RequestSummaryView> result = requestSummaryRepo.findAll(
                specBuilder.build(filterDTO)
        );

        List<String> csvLines = new ArrayList<>();
        csvLines.add("id,host,path,avgHeaders,avgParams,requestTime");

        for (RequestSummaryView row : result) {
            csvLines.add(String.format("%d,%s,%s,%.2f,%.2f,%s",
                    row.getId(),
                    row.getHost(),
                    row.getPath(),
                    row.getAvgHeaders(),
                    row.getAvgParams(),
                    row.getRequestTime()
            ));
        }

        return csvLines;
    }


    private String generateCsvFile(List<String> data) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String filePath = "reports/report_" + timestamp + ".csv";

        try {
            Files.createDirectories(Paths.get("reports"));

            try (FileWriter writer = new FileWriter(filePath)) {
                for (String row : data) {
                    writer.append(row).append("\n");
                }
            }

        } catch (IOException e) {
            logger.error("Ошибка записи CSV-файла отчета", e);
            throw new RuntimeException("Не удалось записать файл отчета.", e);
        }

        return filePath;
    }

    public List<Report> getReports() {
        return reportRepository.findAll();
    }

    public Report getReportResult(Long id) {
        return reportRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Отчет с id {} не найден", id);
                    return new RuntimeException("Отчет с таким id не найден: " + id);
                });
    }

    public Page<RequestSummaryView> getReportPaged(Long reportId, Pageable pageable) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> {
                    logger.warn("Отчет с id {} не найден", reportId);
                    return new RuntimeException("Отчет не найден.");
                });

        if (report.getStatus() != ReportStatus.COMPLETED) {
            logger.info("Попытка доступа к еще не завершенному отчету с id {}", reportId);
            throw new RuntimeException("Отчет еще не завершен.");
        }

        RequestSummaryFilterDTO filter = new RequestSummaryFilterDTO();
        filter.setHost(report.getHost());
        filter.setPath(report.getPath());
        filter.setFrom(report.getRequestFrom());
        filter.setTo(report.getRequestTo());
        filter.setMinHeaders(report.getMinHeaders());
        filter.setMinParams(report.getMinParams());

        Specification<RequestSummaryView> spec = specBuilder.build(filter);
        return requestSummaryRepo.findAll(spec, pageable);
    }

}
