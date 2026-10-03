package com.example.book_api_day8.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * GlobalExceptionHandler
 *
 * This class handles exceptions that occur in our REST controllers.
 *
 * Instead of writing exception-handling code inside every controller,
 * we keep it in one central place.
 *
 * Example:
 *
 * BookController
 * AuthorController
 * UserController
 * OrderController
 *
 * All of them can use this class for common exception handling.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * Handles validation errors caused by @Valid.
     *
     * Example:
     *
     * Our BookRequest contains:
     *
     * @NotBlank(message = "Title is required")
     * private String title;
     *
     * If the client sends:
     *
     * {
     *     "title": "",
     *     "price": 500
     * }
     *
     * @Valid detects that the title is blank.
     *
     * Spring throws:
     *
     * MethodArgumentNotValidException
     *
     * This method catches that exception.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception) {


        /*
         * Create a Map to store validation errors.
         *
         * Key   → field name
         * Value → error message
         *
         * Example:
         *
         * "title" → "Title is required"
         * "price" → "Price must be greater than 0"
         */
        Map<String, String> errors = new HashMap<>();


        /*
         * Get all field-level validation errors.
         *
         * For example, if both title and price are invalid:
         *
         * title → Title is required
         * price → Price must be greater than 0
         */
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {

                    /*
                     * error.getField()
                     *
                     * Gets the name of the field that failed validation.
                     *
                     * Example:
                     * "title"
                     */
                    String fieldName = error.getField();


                    /*
                     * error.getDefaultMessage()
                     *
                     * Gets the message that we specified
                     * inside our validation annotation.
                     *
                     * Example:
                     *
                     * @NotBlank(message = "Title is required")
                     *
                     * Result:
                     * "Title is required"
                     */
                    String errorMessage = error.getDefaultMessage();


                    /*
                     * Put the field name and message into our Map.
                     *
                     * Example:
                     *
                     * errors.put(
                     *     "title",
                     *     "Title is required"
                     * );
                     */
                    errors.put(fieldName, errorMessage);
                });


        /*
         * Return the validation errors to the client.
         *
         * HttpStatus.BAD_REQUEST = HTTP 400
         *
         * So the final response will be:
         *
         * HTTP 400 Bad Request
         *
         * {
         *     "title": "Title is required"
         * }
         */
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}