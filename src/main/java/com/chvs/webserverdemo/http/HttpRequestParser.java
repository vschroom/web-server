package com.chvs.webserverdemo.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpRequestParser {

    public HttpRequest parse(HttpRequest httpRequest, InputStream requestData) throws IOException {
        var inputData = new BufferedReader(new InputStreamReader(requestData, UTF_8));
        String readLine;
        byte bodyCnt = 0;
        String body = "";
        readLine = inputData.readLine();
        var line = readLine.split(" ");
        var method = line[0];
        httpRequest.setMethod(method);
        httpRequest.setPath(line[1]);
        httpRequest.setVersion(line[2]);
        fillParams(httpRequest);
        while (!(readLine = inputData.readLine()).isBlank()) {
            if (readLine.equals("\n")) {
                ++bodyCnt;
            }
            if (bodyCnt == 2) {
                body += readLine;
            } else {
                var header = readLine.split(": ");
                httpRequest.getHeaders().put(header[0], header[1]);
            }
        }

        httpRequest.setBody(body.getBytes());

        return httpRequest;
    }

    private void fillParams(HttpRequest httpRequest) {
        var path = httpRequest.getPath();
        var splitPath = path.split("\\?");
        if (splitPath.length != 2) {
            return;
        }

        var httpRequestParams = httpRequest.getParams();
        var args = splitPath[1];
        for (var arg : args.split("&")) {
            var params = arg.split("=");
            httpRequestParams.put(params[0], params[1]);
        }
    }
}
