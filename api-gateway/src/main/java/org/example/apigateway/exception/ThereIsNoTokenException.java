package org.example.apigateway.exception;

public class ThereIsNoTokenException extends RuntimeException {
    public ThereIsNoTokenException(String message) {
        super(message);
    }
}
