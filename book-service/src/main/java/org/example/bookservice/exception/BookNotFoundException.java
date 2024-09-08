package org.example.bookservice.exception;


// Exception to handle situation when book with certain ID is no found

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }

}
