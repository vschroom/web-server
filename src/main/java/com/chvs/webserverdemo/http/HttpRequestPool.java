package com.chvs.webserverdemo.http;

import java.util.Optional;
import java.util.concurrent.ConcurrentLinkedQueue;

import static java.util.Optional.ofNullable;

public class HttpRequestPool {

    private static final int POOL_CAPACITY = 100;

    private static final ConcurrentLinkedQueue<HttpRequest> HTTP_REQUEST_QUEUE = new ConcurrentLinkedQueue<>();

    static {
        for (int i = 0; i < POOL_CAPACITY; i++) {
            HTTP_REQUEST_QUEUE.add(HttpRequest.create());
        }
    }

    public static Optional<HttpRequest> getRequest() {
        return ofNullable(HTTP_REQUEST_QUEUE.poll());
    }

    public static void putBack(HttpRequest httpRequest) {
        httpRequest.clearHttpRequest();
        HTTP_REQUEST_QUEUE.add(httpRequest);
    }
}
