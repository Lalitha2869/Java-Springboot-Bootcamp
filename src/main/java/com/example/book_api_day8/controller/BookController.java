package com.example.book_api_day8.controller;

import com.example.book_api_day8.dto.BookRequest;
import com.example.book_api_day8.dto.BookResponse;
import com.example.book_api_day8.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    // Constructor Injection
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // GET ALL
//    @GetMapping
//    public ResponseEntity<List<BookResponse>> getAllBooks() {
//
//        return ResponseEntity.ok(
//                bookService.getAllBooks()
//        );
//    }
    @GetMapping
    public Page<BookResponse> getBooks(
            Pageable pageable) {

        return bookService.getBooks(pageable);
    }

    // GET BY ID
//    @GetMapping("/{id}")
//    public ResponseEntity<BookResponse> getBookById(
//            @PathVariable Long id) {
//
//        return ResponseEntity.ok(
//                bookService.getBookById(id)
//        );
//    }


    // CREATE
    @PostMapping
    public ResponseEntity<BookResponse> createBook(
            @Valid @RequestBody BookRequest request) {

        BookResponse response =
                bookService.createBook(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @PathVariable Long id) {

        // Throws ResourceNotFoundException (-> 404) if the book doesn't exist
        bookService.getBookById(id);

        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }
}