package com.chvs.webserverdemo.http;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HttpRequestPool {

    private static final int POOL_CAPACITY = 100;

    private static final ConcurrentLinkedQueue<HttpRequest> HTTP_REQUEST_QUEUE = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < POOL_CAPACITY; i++) {
            HTTP_REQUEST_QUEUE.add(HttpRequest.create());
        }
    }

    public static HttpRequest getRequest() {
        var httpRequest = HTTP_REQUEST_QUEUE.poll();
        if (httpRequest == null) {
            throw new IllegalStateException("Количество свободных запросов истекло. Необходимо увеличить количество, либо дождаться завершения выполнения других");
        }

        return httpRequest;
    }

    public static void addRequest(HttpRequest httpRequest) {
        HTTP_REQUEST_QUEUE.add(httpRequest);
    }
}
