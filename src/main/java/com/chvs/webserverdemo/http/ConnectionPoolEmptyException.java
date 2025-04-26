package com.chvs.webserverdemo.http;

public class ConnectionPoolEmptyException extends HttpException {

    public ConnectionPoolEmptyException() {
        super(
                "Количество свободных запросов истекло. Необходимо увеличить количество, либо дождаться завершения выполнения других",
                ResponseStatusCode.INTERNAL_SERVER_ERROR
        );
    }
}
