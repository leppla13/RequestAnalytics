package ru.leppla.RequestAnalytics.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.leppla.RequestAnalytics.dto.RequestSummaryFilterDTO;
import ru.leppla.RequestAnalytics.entity.RequestSummaryView;
import ru.leppla.RequestAnalytics.service.RequestSummaryService;

@RestController
@RequestMapping("/api/request-summary")
public class RequestSummaryController {

    private final RequestSummaryService service;

    public RequestSummaryController(RequestSummaryService service) {
        this.service = service;
    }

    @PostMapping
    public Page<RequestSummaryView> getFiltered(
            @RequestBody RequestSummaryFilterDTO filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return service.getFilteredSummaries(
                filter.getHost(),
                filter.getPath(),
                filter.getFrom(),
                filter.getTo(),
                filter.getMinHeaders(),
                filter.getMinParams(),
                PageRequest.of(page, size)
        );
    }
}