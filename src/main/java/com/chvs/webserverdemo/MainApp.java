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
            for (;;) {
                try {
                    var acceptSocket = serverSocket.accept();
                    CompletableFuture.runAsync(
                            () -> processRequest(acceptSocket, httpRequestParser, customApi),
                            CustomThreadPoolExecutor.poolExecutor
                    );
                } catch (Exception e) {
                    // переделать на норм логирование
                    System.out.println("Error!!!");
                    System.out.println(e.getMessage());
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void processRequest(
            Socket acceptSocket,
            HttpRequestParser httpRequestParser,
            CustomApi customApi
    ) {
        var response = process(acceptSocket, httpRequestParser, customApi);
        try (var out = acceptSocket.getOutputStream()) {
            out.write(response.toResponse());
            System.out.println("Запрос обработан.");
        } catch (IOException e) {
            // надо подумать, т.к. здесь сложно вернуть ответ
            throw new RuntimeException(e);
        } finally {
            try {
                acceptSocket.close();
                // обработать норм.
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            HttpResponsePool.putBack(response);
        }
    }

    private static HttpResponse process(Socket acceptSocket,
                                        HttpRequestParser httpRequestParser,
                                        CustomApi customApi) {
        var httpResponse = HttpResponsePool.getResponse();
        var httpRequest = HttpRequestPool.getRequest();
        try {
            var in = acceptSocket.getInputStream();
            httpRequestParser.parse(httpRequest, httpResponse, in);
            if (httpResponse.getStatusCode() != null) {
                return httpResponse;
            }

            customApi.process(httpRequest, httpResponse);

            return httpResponse;
        } catch (IOException e) {
            httpResponse.setStatusCode(SERVICE_UNAVAILABLE);
            return httpResponse;
        } finally {
            HttpRequestPool.putBack(httpRequest);
        }
    }
}
