package com.chvs.webserverdemo.http;

public class RequestConnectionPoolEmptyException extends RuntimeException {

    public RequestConnectionPoolEmptyException() {
        throw new IllegalStateException(
                "Количество свободных запросов истекло. Необходимо увеличить количество, либо дождаться завершения выполнения других"
        );
    }
}
