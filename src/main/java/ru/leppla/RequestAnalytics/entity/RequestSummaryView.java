package ru.leppla.RequestAnalytics.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;

@Entity
@Table(name = "request_summary_view")
@Immutable
public class RequestSummaryView {

    @Id
    private Long id;

    private String host;
    private String path;

    @Column(name = "avg_headers")
    private Double avgHeaders;

    @Column(name = "avg_params")
    private Double avgParams;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    public Long getId() { return id; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public Double getAvgHeaders() { return avgHeaders; }
    public void setAvgHeaders(Double avgHeaders) { this.avgHeaders = avgHeaders; }

    public Double getAvgParams() { return avgParams; }
    public void setAvgParams(Double avgParams) { this.avgParams = avgParams; }

    public LocalDateTime getRequestTime() { return requestTime; }
    public void setRequestTime(LocalDateTime requestTime) { this.requestTime = requestTime; }
}