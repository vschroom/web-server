package com.chvs.webserverdemo.http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponse {

    private String version = "HTTP/1.1";
    private ResponseStatusCode responseStatusCode;
    private Map<HttpHeader, String> headers = new HashMap<>();
    private byte[] body;

    public HttpResponse() {
    }

    public HttpResponse(String version,
                        ResponseStatusCode responseStatusCode,
                        Map<HttpHeader, String> headers,
                        byte[] body) {
        this.version = version;
        this.responseStatusCode = responseStatusCode;
        this.headers = headers;
        this.body = body;
    }

    public static HttpResponse create() {
        return new HttpResponse();
    }

    public String getVersion() {
        return version;
    }

    public ResponseStatusCode getResponseStatusCode() {
        return responseStatusCode;
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

    public void setResponseStatusCode(ResponseStatusCode responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public void setHeaders(Map<HttpHeader, String> headers) {
        this.headers = headers;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public byte[] toResponse() {
        return ("%s %s %s".formatted(version, responseStatusCode.getCode(), responseStatusCode.name())
                + "\n" +
                headers.entrySet()
                        .stream()
                        .map(e -> e.getKey() + ": " + e.getValue())
                        .collect(Collectors.joining("\n"))
                + "\n\n" +
                new String(body)
        ).getBytes();
    }
}
