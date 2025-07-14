package ru.leppla.RequestAnalytics.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "headers")
public class Header {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String headerName;
    private String headerValue;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    public Long getId() {
        return id;
    }

    public String getHeaderName() { return headerName; }
    public void setHeaderName(String headerName) { this.headerName = headerName; }

    public String getHeaderValue() { return headerValue; }

    public void setHeaderValue(String headerValue) { this.headerValue = headerValue; }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}