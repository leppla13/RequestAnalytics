package ru.leppla.RequestAnalytics.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "params")
public class Param {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paramName;
    private String paramValue;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    public Long getId() {
        return id;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getParamName() { return paramName; }
    public void setParamName(String paramName) { this.paramName = paramName; }

    public String getParamValue() { return paramValue; }
    public void setParamValue(String paramValue) { this.paramValue = paramValue; }
}