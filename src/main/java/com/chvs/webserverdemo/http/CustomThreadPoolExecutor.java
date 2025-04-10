package com.chvs.webserverdemo.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolExecutor {

    public static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() + 20,
            180000,
            TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<>(50)
    );
}
