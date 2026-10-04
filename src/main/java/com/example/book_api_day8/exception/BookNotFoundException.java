package com.example.book_api_day8.exception;



/**
 * Custom exception used when a requested book
 * does not exist in the database.
 */
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String message) {
        super(message);
    }
}