package ru.leppla.RequestAnalytics.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String host;
    private String path;
    private LocalDateTime requestFrom;
    private LocalDateTime requestTo;

    private String status;
    private String filePath;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "min_headers")
    private Double minHeaders;

    @Column(name = "min_params")
    private Double minParams;

    public Double getMinHeaders() {
        return minHeaders;
    }
    public void setMinHeaders(Double minHeaders) {
        this.minHeaders = minHeaders;
    }

    public Double getMinParams() {
        return minParams;
    }
    public void setMinParams(Double minParams) {
        this.minParams = minParams;
    }

    public Long getId() { return id; }

    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }

    public LocalDateTime getRequestFrom() { return requestFrom; }
    public void setRequestFrom(LocalDateTime requestFrom) { this.requestFrom = requestFrom; }

    public LocalDateTime getRequestTo() { return requestTo; }
    public void setRequestTo(LocalDateTime requestTo) { this.requestTo = requestTo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
