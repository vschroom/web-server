package com.chvs.webserverdemo.http;

public class HttpException extends RuntimeException {

    private final ResponseStatusCode statusCode;

    public HttpException(String message, ResponseStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ResponseStatusCode getStatusCode() {
        return statusCode;
    }
}
