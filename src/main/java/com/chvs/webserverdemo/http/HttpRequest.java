package com.chvs.webserverdemo.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private HttpRequestType method;
    private String version;
    private String path;
    private final Map<HttpHeader, String> headers = new HashMap<>();
    private byte[] body;
    private HttpRequestType requestType;

    private HttpRequest() {
    }

    static HttpRequest create() {
        return new HttpRequest();
    }

    public void clearHttpRequest() {
        this.method = null;
        this.version = null;
        this.path = null;
        this.headers.clear();
        this.body = null; // сделать фикс размер?
        this.requestType = null;
    }

    HttpRequestType getMethod() {
        return method;
    }

    void setMethod(String method) {
        this.method = HttpRequestType.valueOf(method);
    }

    String getVersion() {
        return version;
    }

    void setVersion(String version) {
        this.version = version;
    }

    String getPath() {
        return path;
    }

    void setPath(String path) {
        this.path = path;
    }

    Map<HttpHeader, String> getHeaders() {
        return headers;
    }

    byte[] getBody() {
        return body;
    }

    void setBody(byte[] body) {
        this.body = body;
    }

    HttpRequestType getRequestType() {
        return requestType;
    }

    void setRequestType(HttpRequestType requestType) {
        this.requestType = requestType;
    }
}
