package com.example.book_api.controller;

import com.example.book_api.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        books.add(new Book(101, "Clean Code", "Robert Martin", 500));
        books.add(new Book(102, "Effective Java", "Joshua Bloch", 750));
        books.add(new Book(103, "Java Basics", "James Gosling", 400));
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable int id,
            @RequestBody Book updatedBook) {

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                updatedBook.setId(id);
                books.set(i, updatedBook);
                return ResponseEntity.ok(updatedBook);
            }
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {

        boolean removed = books.removeIf(book -> book.getId() == id);

        if (!removed) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
