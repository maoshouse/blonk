package com.maoshouse.blonk.exception;

public class RestApiException extends Exception {

    public RestApiException(String message) {
        super(message);
    }

    public RestApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
