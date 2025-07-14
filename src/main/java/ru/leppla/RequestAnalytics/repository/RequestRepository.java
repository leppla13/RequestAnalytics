package ru.leppla.RequestAnalytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.leppla.RequestAnalytics.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}