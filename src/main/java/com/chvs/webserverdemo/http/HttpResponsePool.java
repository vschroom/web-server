package com.chvs.webserverdemo.http;

import java.util.concurrent.ConcurrentLinkedQueue;

public class HttpResponsePool {

    private static final int POOL_CAPACITY = 100;

    private static final ConcurrentLinkedQueue<HttpResponse> HTTP_RESPONSE_QUEUE = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < POOL_CAPACITY; i++) {
            HTTP_RESPONSE_QUEUE.add(HttpResponse.create());
        }
    }

    public static HttpResponse getResponse() {
        var httpResponse = HTTP_RESPONSE_QUEUE.poll();
        if (httpResponse == null) {
            throw new IllegalStateException("Очередь ответов переполнена");
        }

        return httpResponse;
    }

    public static void addResponse(HttpResponse httpResponse) {
        HTTP_RESPONSE_QUEUE.add(httpResponse);
    }
}
