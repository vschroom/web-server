package com.chvs.webserverdemo.http;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpResponsePool {

    private static final AtomicInteger CURRENT_CAPACITY = new AtomicInteger(0);

    private static final int POOL_CAPACITY = Runtime.getRuntime().availableProcessors() * 10;

    private static final ConcurrentLinkedQueue<HttpResponse> HTTP_RESPONSE_QUEUE = new ConcurrentLinkedQueue<>();

    public static HttpResponse getResponse() {
        if (CURRENT_CAPACITY.getAndIncrement() <= POOL_CAPACITY) {
            HTTP_RESPONSE_QUEUE.add(HttpResponse.create());
            return HTTP_RESPONSE_QUEUE.poll();
        } else {
            throw new ConnectionPoolEmptyException();
        }
    }

    public static void putBack(HttpResponse httpResponse) {
        httpResponse.clearResponse();
        HTTP_RESPONSE_QUEUE.add(httpResponse);
        CURRENT_CAPACITY.decrementAndGet();
    }
}
