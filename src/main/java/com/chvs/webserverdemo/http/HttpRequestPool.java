package com.chvs.webserverdemo.http;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class HttpRequestPool {

    private static final int POOL_CAPACITY = 20;
    static final Queue<HttpRequest> HTTP_REQUEST_QUEUE = new ArrayBlockingQueue<>(POOL_CAPACITY);

    static {
        for (int i = 0; i < POOL_CAPACITY; i++) {
            HTTP_REQUEST_QUEUE.add(HttpRequest.create());
        }
    }
}
