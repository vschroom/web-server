package com.chvs.webserverdemo;

import com.chvs.webserverdemo.http.CustomApi;
import com.chvs.webserverdemo.http.CustomThreadPoolExecutor;
import com.chvs.webserverdemo.http.HttpRequestParser;
import com.chvs.webserverdemo.http.HttpResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class MainApp {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(18080)) {
            System.out.println("Initialize server");
            var httpRequestParser = new HttpRequestParser();
            var customApi = new CustomApi();
            for (;;) {
                try (Socket acceptSocket = serverSocket.accept()) {
                    var response = CompletableFuture.supplyAsync(
                            () -> process(acceptSocket, httpRequestParser, customApi),
                            CustomThreadPoolExecutor.poolExecutor
                    );

                    var out = acceptSocket.getOutputStream();
                    out.write(response.get().toResponse());
                    System.out.println("Client disconnected!");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpResponse process(Socket acceptSocket,
                                        HttpRequestParser httpRequestParser,
                                        CustomApi customApi) {
        try {
            var httpRequest = httpRequestParser.parse(acceptSocket.getInputStream());
            var httpResponse = new HttpResponse();
            customApi.process(httpRequest, httpResponse);

            httpRequest.clearHttpRequest();

            return httpResponse;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
