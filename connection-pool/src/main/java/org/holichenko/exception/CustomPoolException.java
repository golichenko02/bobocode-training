package org.holichenko.exception;

public class CustomPoolException extends RuntimeException{

    public CustomPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}
