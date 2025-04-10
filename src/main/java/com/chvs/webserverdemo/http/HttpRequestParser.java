package com.chvs.webserverdemo.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequestParser {

    public HttpRequest parse(InputStream requestData) throws IOException {
        var inputData = new BufferedReader(new InputStreamReader(requestData, UTF_8));
        var httpRequest = HttpRequestPool.getRequest();
        String readLine;
        boolean firstLineUnread = true;
        byte bodyCnt = 0;
        String body = "";
        while (!(readLine = inputData.readLine()).isBlank()) {
            if (readLine.equals("\n")) {
                ++bodyCnt;
            }
            if (firstLineUnread) {
                var line = readLine.split(" ");
                var method = line[0];
                httpRequest.setMethod(method);
                httpRequest.setPath(line[1]);
                httpRequest.setVersion(line[2]);
                firstLineUnread = false;
            } else if (bodyCnt == 2) {
                body += readLine;
            } else {
                var header = readLine.split(": ");
                httpRequest.getHeaders().put(header[0], header[1]);
            }
        }

        httpRequest.setBody(body.getBytes());

        return httpRequest;
    }
}
