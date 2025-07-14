package ru.leppla.RequestAnalytics.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.leppla.RequestAnalytics.dto.RequestSummaryFilterDTO;
import ru.leppla.RequestAnalytics.entity.RequestSummaryView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


@Component
public class RequestSummarySpecification {

    public Specification<RequestSummaryView> build(RequestSummaryFilterDTO filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getHost() != null && !filter.getHost().isEmpty())
                predicates.add(cb.equal(root.get("host"), filter.getHost()));

            if (filter.getPath() != null && !filter.getPath().isEmpty())
                predicates.add(cb.equal(root.get("path"), filter.getPath()));

            if (filter.getFrom() != null)
                predicates.add(cb.greaterThanOrEqualTo(root.get("requestTime"), filter.getFrom()));

            if (filter.getTo() != null)
                predicates.add(cb.lessThanOrEqualTo(root.get("requestTime"), filter.getTo()));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


    public static Specification<RequestSummaryView> hasHost(String host) {
        return (root, query, cb) ->
                host == null ? null : cb.like(cb.lower(root.get("host")), "%" + host.toLowerCase() + "%");
    }

    public static Specification<RequestSummaryView> hasPath(String path) {
        return (root, query, cb) ->
                path == null ? null : cb.like(cb.lower(root.get("path")), "%" + path.toLowerCase() + "%");
    }

    public static Specification<RequestSummaryView> requestTimeBetween(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> {
            if (from != null && to != null) return cb.between(root.get("requestTime"), from, to);

            else if (from != null) return cb.greaterThanOrEqualTo(root.get("requestTime"), from);

            else if (to != null) return cb.lessThanOrEqualTo(root.get("requestTime"), to);

            return null;
        };
    }

    public static Specification<RequestSummaryView> avgHeadersGreaterThanOrEq(Double min) {
        return (root, query, cb) ->
                min == null ? null : cb.greaterThanOrEqualTo(root.get("avgHeaders"), min);
    }

    public static Specification<RequestSummaryView> avgParamsGreaterThanOrEq(Double min) {
        return (root, query, cb) ->
                min == null ? null : cb.greaterThanOrEqualTo(root.get("avgParams"), min);
    }
}