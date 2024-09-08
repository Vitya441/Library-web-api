package org.example.bookservice.exception;

public class BookWithIsbnExistException extends RuntimeException {
    public BookWithIsbnExistException(String message) {
        super(message);
    }
}
