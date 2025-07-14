package ru.leppla.RequestAnalytics.dto;

import java.time.LocalDateTime;

public class RequestSummaryFilterDTO {
    private String host;
    private String path;
    private LocalDateTime from;
    private LocalDateTime to;
    private Double minHeaders;
    private Double minParams;

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

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

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
}
