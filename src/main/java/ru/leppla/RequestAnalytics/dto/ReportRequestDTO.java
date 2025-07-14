package ru.leppla.RequestAnalytics.dto;

import java.time.LocalDateTime;

public class ReportRequestDTO {

    private String host;
    private String path;
    private LocalDateTime requestFrom;
    private LocalDateTime requestTo;

    private Double minHeaders;
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

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getRequestFrom() {
        return requestFrom;
    }

    public void setRequestFrom(LocalDateTime requestFrom) {
        this.requestFrom = requestFrom;
    }

    public LocalDateTime getRequestTo() {
        return requestTo;
    }

    public void setRequestTo(LocalDateTime requestTo) {
        this.requestTo = requestTo;
    }
}
