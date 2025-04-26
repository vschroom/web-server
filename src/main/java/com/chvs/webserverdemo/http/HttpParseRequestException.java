package com.chvs.webserverdemo.http;

public class HttpParseRequestException extends HttpException {

    public HttpParseRequestException() {
        super("Failed while parsing request", ResponseStatusCode.BAD_REQUEST);
    }
}
