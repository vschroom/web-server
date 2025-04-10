package com.chvs.webserverdemo;

import com.chvs.webserverdemo.http.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

import static com.chvs.webserverdemo.http.ResponseStatusCode.SERVICE_UNAVAILABLE;

public class MainApp {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(18080)) {
            System.out.printf("Initialize server on port: %s%n", 18080);
            var httpRequestParser = new HttpRequestParser();
            var customApi = new CustomApi();
            for (; ; ) {
                CompletableFuture.runAsync(() -> processRequest(serverSocket, httpRequestParser, customApi));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processRequest(
            ServerSocket serverSocket,
            HttpRequestParser httpRequestParser,
            CustomApi customApi
    ) {
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

    private static HttpResponse process(Socket acceptSocket,
                                        HttpRequestParser httpRequestParser,
                                        CustomApi customApi) {
        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;
        try {
            httpRequest = httpRequestParser.parse(acceptSocket.getInputStream());
            httpResponse = HttpResponsePool.getResponse();
            customApi.process(httpRequest, httpResponse);

            return httpResponse;
        } catch (IOException e) {
            var response = HttpResponsePool.getResponse();
            response.setResponseStatusCode(SERVICE_UNAVAILABLE);
            return response;
        } finally {
            if (httpRequest != null) {
                httpRequest.clearHttpRequest();
                HttpRequestPool.addRequest(httpRequest);
            }
            if (httpResponse != null) {
                HttpResponsePool.addResponse(httpResponse);
            }
        }
    }
}
