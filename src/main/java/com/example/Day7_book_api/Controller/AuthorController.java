package com.example.Day7_book_api.Controller;

import com.example.Day7_book_api.Model.Author;
import com.example.Day7_book_api.Service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    // ---------------------------------------------------------
    // CONSTRUCTOR INJECTION
    // ---------------------------------------------------------
    /*
     * Spring automatically provides AuthorService
     * when it creates AuthorController.
     */
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    // =========================================================
    // GET ALL AUTHORS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {

        // Get all authors from MySQL through the service.
        List<Author> authors = authorService.getAllAuthors();

        // Return HTTP 200 OK with the list.
        return ResponseEntity.ok(authors);
    }


    // =========================================================
    // GET AUTHOR BY ID
    // =========================================================

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(
            @PathVariable Long id) {

        /*
         * Search for the author using the ID.
         *
         * Optional is used because the author may
         * or may not exist.
         */
        return authorService.getAuthorById(id)

                // Author exists → HTTP 200.
                .map(ResponseEntity::ok)

                // Author doesn't exist → HTTP 404.
                .orElseGet(
                        () -> ResponseEntity.notFound().build()
                );
    }


    // =========================================================
    // CREATE AUTHOR
    // =========================================================

    @PostMapping
    public ResponseEntity<Author> createAuthor(
            @RequestBody Author author) {

        /*
         * Request JSON is converted into an
         * Author Java object by Spring.
         *
         * Example request:
         *
         * {
         *     "name": "Robert Martin"
         * }
         */

        // Save the author into MySQL.
        Author savedAuthor = authorService.saveAuthor(author);

        /*
         * HTTP 201 CREATED means a new resource
         * was successfully created.
         */
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedAuthor);
    }


    // =========================================================
    // UPDATE AUTHOR
    // =========================================================

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(
            @PathVariable Long id,
            @RequestBody Author updatedAuthor) {

        /*
         * First check whether the author exists.
         */
        if (authorService.getAuthorById(id).isEmpty()) {

            // Author doesn't exist → HTTP 404.
            return ResponseEntity.notFound().build();
        }

        /*
         * Use the ID from the URL.
         *
         * Example:
         *
         * PUT /api/authors/1
         *
         * The ID will remain 1 even if the request
         * body doesn't contain an ID.
         */
        updatedAuthor.setId(id);

        /*
         * Save the updated author.
         *
         * Since the ID already exists, JPA performs
         * an UPDATE instead of creating a new row.
         */
        Author savedAuthor =
                authorService.saveAuthor(updatedAuthor);

        // Return HTTP 200 with updated author.
        return ResponseEntity.ok(savedAuthor);
    }


    // =========================================================
    // DELETE AUTHOR
    // =========================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(
            @PathVariable Long id) {

        /*
         * Check whether the author exists before
         * trying to delete it.
         */
        if (authorService.getAuthorById(id).isEmpty()) {

            // Author doesn't exist → HTTP 404.
            return ResponseEntity.notFound().build();
        }

        /*
         * Delete the author from MySQL.
         *
         * IMPORTANT:
         * If books are still connected to this author,
         * MySQL may reject the deletion because the
         * books table contains author_id as a foreign key.
         */
        authorService.deleteAuthor(id);

        /*
         * HTTP 204 NO CONTENT means the deletion
         * was successful and there is no response body.
         */
        return ResponseEntity.noContent().build();
    }
}