package com.chvs.webserverdemo.http;

import java.util.Map;

public class HttpResponse {

    private String version;
    private ResponseStatusCode responseStatusCode;
    private Map<HttpHeader, String> headers;
    private byte[] body;

    public HttpResponse(String version, ResponseStatusCode responseStatusCode, Map<HttpHeader, String> headers, byte[] body) {
        this.version = version;
        this.responseStatusCode = responseStatusCode;
        this.headers = headers;
        this.body = body;
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
}
