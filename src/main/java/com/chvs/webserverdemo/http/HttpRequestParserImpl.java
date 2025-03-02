package com.chvs.webserverdemo.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParserImpl implements HttpRequestParser {

    @Override
    public HttpRequest parse(InputStream requestData) throws IOException {
        var inputData = new BufferedReader(new InputStreamReader(
                requestData,
                StandardCharsets.UTF_8));
        int count = 0;
        Map<HttpHeader, String> headers = new HashMap<>();
        HttpRequest httpRequest = null;
        String readLine;
        while (!(readLine = inputData.readLine()).isBlank()) {
            if (count == 0) {
                var line = readLine.split(" ");
                var method = line[0];
                if (method.equals("GET")) {
                    httpRequest = new GetHttpRequest();
                    httpRequest.setPath(line[1]);
                    httpRequest.setVersion(line[2]);
                }
                count++;
            } else {
                var header = readLine.split(": ");
                var httpHeader = HttpHeader.parse(header[0]);
                headers.put(httpHeader, header[1]);
            }
        }

        return httpRequest;
    }
}
