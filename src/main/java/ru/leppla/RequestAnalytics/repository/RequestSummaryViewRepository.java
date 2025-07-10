package ru.leppla.RequestAnalytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.leppla.RequestAnalytics.entity.RequestSummaryView;

public interface RequestSummaryViewRepository extends JpaRepository<RequestSummaryView, Long>, JpaSpecificationExecutor<RequestSummaryView> {
}