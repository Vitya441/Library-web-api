package org.example.bookservice.exception;


// Exception to handle situation when book with certain ID is already exist

public class BookWithIsbnExistException extends RuntimeException {
    
    public BookWithIsbnExistException(String message) {
        super(message);
    }
}
