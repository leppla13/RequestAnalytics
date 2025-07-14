package ru.leppla.RequestAnalytics.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.leppla.RequestAnalytics.entity.RequestSummaryView;
import ru.leppla.RequestAnalytics.repository.RequestSummaryViewRepository;
import ru.leppla.RequestAnalytics.specification.RequestSummarySpecification;

import java.time.LocalDateTime;

@Service
public class RequestSummaryService {

    private final RequestSummaryViewRepository repository;

    public RequestSummaryService(RequestSummaryViewRepository repository) {
        this.repository = repository;
    }

    public Page<RequestSummaryView> getFilteredSummaries(String host, String path,
                                                         LocalDateTime from, LocalDateTime to,
                                                         Double minHeaders, Double minParams,
                                                         Pageable pageable) {

        Specification<RequestSummaryView> spec = null;

        if (host != null && !host.isEmpty())
            spec = RequestSummarySpecification.hasHost(host);

        if (path != null && !path.isEmpty())
            spec = spec == null ? RequestSummarySpecification.hasPath(path) : spec.and(RequestSummarySpecification.hasPath(path));

        if (from != null || to != null)
            spec = spec == null ? RequestSummarySpecification.requestTimeBetween(from, to) : spec.and(RequestSummarySpecification.requestTimeBetween(from, to));

        if (minHeaders != null)
            spec = spec == null ? RequestSummarySpecification.avgHeadersGreaterThanOrEq(minHeaders) : spec.and(RequestSummarySpecification.avgHeadersGreaterThanOrEq(minHeaders));

        if (minParams != null)
            spec = spec == null ? RequestSummarySpecification.avgParamsGreaterThanOrEq(minParams) : spec.and(RequestSummarySpecification.avgParamsGreaterThanOrEq(minParams));

        return repository.findAll(spec, pageable);
    }
}
