package com.chvs.webserverdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainApp {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(18080)) {
            System.out.println("Initialize server");
            for (;;) {
                Socket acceptSocket = serverSocket.accept();
                System.out.println("Income client request");
                System.out.println("================================================================");

                try (BufferedReader inputData = new BufferedReader(new InputStreamReader(
                        acceptSocket.getInputStream(),
                        StandardCharsets.UTF_8));
                     PrintWriter output = new PrintWriter(acceptSocket.getOutputStream())) {
                    StringBuilder requestData = new StringBuilder();
                    String readLine;
                    while (!(readLine = inputData.readLine()).isBlank()) {
                        requestData
                                .append(readLine)
                                .append("\n");
                    }

                    System.out.println(requestData);

                    System.out.println("================================================================");

                    var request = requestData.toString();
                    var requestFirstLine = request.split("\n")[0];
                    var splitFirstLine = requestFirstLine.split(" ");
                    String method = splitFirstLine[0];
                    String path = splitFirstLine[1];
                    String protocol = splitFirstLine[2];
                    System.out.println(method);
                    System.out.println(path);
                    System.out.println(protocol);

                    Map<String, String> headers = getHeaders(request);
                    headers.forEach((key, value) -> System.out.println(key + ": " + value));

                    System.out.println("================================================================");

                    // response
                    output.println("HTTP/1.1 201 OK");
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println("<p>Привет всем!</p>");
                    output.println("<p>Headers: %s</p>".formatted(headers));
                    output.flush();

                    System.out.println("Client disconnected!");
                } catch (IOException e) {
                    acceptSocket.close();
                    throw new RuntimeException(e);
                }
                acceptSocket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<String, String> getHeaders(String request) {
        Map<String, String> headers = new LinkedHashMap<>();
        var requestParts = request.split("\n");
        for (int i = 1; i < requestParts.length; i++) {
            var requestHeaders = requestParts[i].split(": ");
            headers.put(requestHeaders[0], requestHeaders[1]);
        }

        return headers;
    }
}
