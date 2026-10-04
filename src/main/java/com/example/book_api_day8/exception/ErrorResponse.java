package com.example.book_api_day8.exception;



import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Standard JSON structure returned when an API error occurs.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {

    private int status;

    private String message;
}