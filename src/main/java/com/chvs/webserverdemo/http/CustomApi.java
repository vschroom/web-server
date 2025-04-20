package com.chvs.webserverdemo.http;

import java.util.Map;

public class CustomApi {

    public void process(HttpRequest request, HttpResponse response) {
        if (request == null) {
            return;
        }
        response.setHeaders(Map.of());
        response.setStatusCode(ResponseStatusCode.CREATED);
        response.setHeaders(Map.of(HttpHeader.CONTENT_TYPE, "application/json"));
        response.setBody("Simple response".getBytes());
    }
}
