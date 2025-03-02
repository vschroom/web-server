package com.chvs.webserverdemo.http;

import java.util.Map;

public abstract class HttpRequest {

    private String method;
    private String version;
    private String path;
    private Map<HttpHeader, String> headers;

    public HttpRequest() {
    }

    public HttpRequest(String method) {
        this.method = method;
    }

    public HttpRequest(String method, String version, String path, Map<HttpHeader, String> headers) {
        this.method = method;
        this.version = version;
        this.path = path;
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }

    public String getPath() {
        return path;
    }

    public Map<HttpHeader, String> getHeaders() {
        return headers;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHeaders(Map<HttpHeader, String> headers) {
        this.headers = headers;
    }
}
