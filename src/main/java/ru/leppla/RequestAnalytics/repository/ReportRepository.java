package ru.leppla.RequestAnalytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.leppla.RequestAnalytics.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
