package com.chvs.webserverdemo.http;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpRequestPool {

    private static final AtomicInteger CURRENT_CAPACITY = new AtomicInteger(0);
    private static final int POOL_CAPACITY = Runtime.getRuntime().availableProcessors() * 10;

    private static final ConcurrentLinkedQueue<HttpRequest> HTTP_REQUEST_QUEUE = new ConcurrentLinkedQueue<>();

    public static HttpRequest getRequest() {
        if (CURRENT_CAPACITY.getAndIncrement() <= POOL_CAPACITY) {
            HTTP_REQUEST_QUEUE.add(HttpRequest.create());
            return HTTP_REQUEST_QUEUE.poll();
        } else {
            throw new ConnectionPoolEmptyException();
        }
    }

    public static void putBack(HttpRequest httpRequest) {
        httpRequest.clearHttpRequest();
        HTTP_REQUEST_QUEUE.add(httpRequest);
        CURRENT_CAPACITY.decrementAndGet();
    }
}
