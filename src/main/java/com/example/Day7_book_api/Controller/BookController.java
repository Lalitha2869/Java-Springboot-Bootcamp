package com.example.Day7_book_api.Controller;

import com.example.Day7_book_api.Model.Author;
import com.example.Day7_book_api.Model.Book;
import com.example.Day7_book_api.Service.AuthorService;
import com.example.Day7_book_api.Service.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    // Constructor injection.
    public BookController(
            BookService bookService,
            AuthorService authorService) {

        this.bookService = bookService;
        this.authorService = authorService;
    }

    // =========================================================
    // GET ALL BOOKS
    // =========================================================

    @GetMapping
    public List<Book> getAllBooks() {

        return bookService.getAllBooks();
    }

    // =========================================================
    // GET BOOK BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @PathVariable Long id) {

        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }

    // =========================================================
    // CREATE BOOK
    // =========================================================

    @PostMapping
    public ResponseEntity<?> createBook(
            @RequestBody Book book) {

        /*
         * We expect the request body to contain:
         *
         * {
         *     "title": "Clean Code",
         *     "price": 500,
         *     "author": {
         *         "id": 1
         *     }
         * }
         */

        // Check whether an author was supplied.
        if (book.getAuthor() == null ||
                book.getAuthor().getId() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Author ID is required.");
        }

        // Get the author ID from the request.
        Long authorId = book.getAuthor().getId();

        // Find the author in MySQL.
        Author author = authorService
                .getAuthorById(authorId)
                .orElse(null);

        // Author doesn't exist.
        if (author == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Author with ID " + authorId + " not found.");
        }

        // Attach the real Author entity to the Book.
        book.setAuthor(author);

        // Save the Book into MySQL.
        Book savedBook = bookService.saveBook(book);

        // Return 201 Created.
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBook);
    }

    // =========================================================
    // UPDATE BOOK
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(
            @PathVariable Long id,
            @RequestBody Book updatedBook) {

        // Check whether the book exists.
        if (bookService.getBookById(id).isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Book with ID " + id + " not found.");
        }

        // Make sure an author was supplied.
        if (updatedBook.getAuthor() == null ||
                updatedBook.getAuthor().getId() == null) {

            return ResponseEntity
                    .badRequest()
                    .body("Author ID is required.");
        }

        // Find the author.
        Long authorId = updatedBook.getAuthor().getId();

        Author author = authorService
                .getAuthorById(authorId)
                .orElse(null);

        if (author == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Author with ID " + authorId + " not found.");
        }

        // Attach author.
        updatedBook.setAuthor(author);

        // Use ID from URL.
        updatedBook.setId(id);

        // Save update.
        Book savedBook = bookService.saveBook(updatedBook);

        return ResponseEntity.ok(savedBook);
    }

    // =========================================================
    // DELETE BOOK
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(
            @PathVariable Long id) {

        if (bookService.getBookById(id).isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Book with ID " + id + " not found.");
        }

        bookService.deleteBook(id);

        return ResponseEntity.noContent().build();
    }

    // =========================================================
    // SEARCH BY TITLE
    // =========================================================

    @GetMapping("/search-by-title")
    public List<Book> searchByTitle(
            @RequestParam String title) {

        return bookService.searchByTitle(title);
    }

    // =========================================================
    // SEARCH BY AUTHOR
    // =========================================================

    @GetMapping("/search-by-author")
    public List<Book> searchByAuthor(
            @RequestParam String authorName) {

        return bookService.searchByAuthor(authorName);
    }

    // =========================================================
    // CUSTOM QUERY
    // =========================================================

    @GetMapping("/search-by-price")
    public List<Book> searchByPrice(
            @RequestParam double price) {

        return bookService.findBooksAbovePrice(price);
    }
}