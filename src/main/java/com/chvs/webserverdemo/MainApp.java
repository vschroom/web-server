package com.chvs.webserverdemo;

import com.chvs.webserverdemo.http.*;

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

    // StringBuilder убрать
    // создать класс Request с полями:
    // метод
    // версия
    // заголовки (headers)
    // path
    // тело (body)
    // размер запроса (читаем байты до символа переноса строки)
    // в идеале читать байты
    // content-length
    // объект response

    // обработчик запроса, обработчик ответа, посеридине хэндлер
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(18080)) {
            System.out.println("Initialize server");
            var httpRequestParser = new HttpRequestParserImpl();
            for (;;) {
                System.out.println("Income client request");
                System.out.println("================================================================");

                try (Socket acceptSocket = serverSocket.accept()) {

                    var httpRequest = httpRequestParser.parse(acceptSocket.getInputStream());

                    System.out.println("================================================================");

                    // response

                    var httpResponse = new HttpResponse(
                            "HTTP/1.1",
                            ResponseStatusCode.CREATED,
                            Map.of(HttpHeader.CONTENT_TYPE,  "text/html; charset=utf-8"),
                            "<p>Привет всем!</p>".getBytes()
                    );
                    /*output.println("HTTP/1.1 201 OK");
                    output.println("Content-Type: text/html; charset=utf-8");
                    output.println();
                    output.println("<p>Привет всем!</p>");
                    output.println("<p>Headers: %s</p>".formatted(headers));
                    output.flush();*/

                    System.out.println("Client disconnected!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
