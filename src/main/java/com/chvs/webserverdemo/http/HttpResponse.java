package com.chvs.webserverdemo.http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponse {

    private String version = "HTTP/1.1";
    private ResponseStatusCode statusCode;
    private Map<HttpHeader, String> headers = new HashMap<>();
    private byte[] body;

    public HttpResponse() {
    }

    public HttpResponse(String version,
                        ResponseStatusCode statusCode,
                        Map<HttpHeader, String> headers,
                        byte[] body) {
        this.version = version;
        this.statusCode = statusCode;
        this.headers = headers;
        this.body = body;
    }

    public void clearResponse() {
        statusCode = null;
        headers.clear();
        body = null;
    }

    public static HttpResponse create() {
        return new HttpResponse();
    }

    public String getVersion() {
        return version;
    }

    public ResponseStatusCode getStatusCode() {
        return statusCode;
    }

    public Map<HttpHeader, String> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setStatusCode(ResponseStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeaders(Map<HttpHeader, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] toResponse() {
        return ("%s %s %s".formatted(version, statusCode.getCode(), statusCode.name())
                + "\n" +
                headers.entrySet()
                        .stream()
                        .map(e -> e.getKey() + ": " + e.getValue())
                        .collect(Collectors.joining("\n"))
                + "\n\n" +
                (body == null ? "" : new String(body))
        ).getBytes();
    }
}
