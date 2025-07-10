package ru.leppla.RequestAnalytics.dto;

import java.util.Map;

public class JsonRequestDTO {
    private String url;
    private String path;
    private Map<String, String> variable_params;
    private Map<String, String> headers;
    private String body;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getVariable_params() {
        return variable_params;
    }

    public void setVariable_params(Map<String, String> variable_params) {
        this.variable_params = variable_params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
