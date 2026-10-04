package com.example.book_api_day8.service;

import com.example.book_api_day8.dto.BookRequest;
import com.example.book_api_day8.dto.BookResponse;
import com.example.book_api_day8.model.Book;
import com.example.book_api_day8.repository.BookRepository;
import com.example.book_api_day8.exception.ResourceNotFoundException;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Service
public class BookService {

    private static final Logger log =
            LoggerFactory.getLogger(BookService.class);

    private final BookRepository bookRepository;

    // Constructor Injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // GET ALL
    public List<BookResponse> getAllBooks() {

        return bookRepository.findAll()
                .stream()
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getPrice()
                ))
                .toList();
    }

    // GET BY ID
//    public BookResponse getBookById(Long id) {
//
//        Book book = bookRepository.findById(id)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "Book with id " + id + " not found"
//                        )
//                );
//
//        return new BookResponse(
//                book.getId(),
//                book.getTitle(),
//                book.getPrice()
//        );
//    }

    public BookResponse getBookById(Long id) {

        log.info("Fetching book with id: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Book with id {} was not found",
                            id
                    );

                    return new ResourceNotFoundException(
                            "Book with id " + id + " not found"
                    );
                });

        log.info(
                "Book with id {} found successfully",
                id
        );

        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getPrice()
        );
    }



    // CREATE
    public BookResponse createBook(BookRequest request) {

        // DTO → Entity
        Book book = new Book();

        book.setTitle(request.getTitle());
        book.setPrice(request.getPrice());

        // Save Entity
        Book savedBook = bookRepository.save(book);

        // Entity → DTO
        return new BookResponse(
                savedBook.getId(),
                savedBook.getTitle(),
                savedBook.getPrice()
        );
    }
    /**
     * Returns books using pagination and sorting.
     *
     * Pageable contains:
     * - page number
     * - page size
     * - sorting information
     */
    public Page<BookResponse> getBooks(Pageable pageable) {

        /*
         * Ask the repository for only the required page.
         *
         * Example:
         *
         * page = 0
         * size = 5
         *
         * Repository gets only the required records.
         */
        return bookRepository
                .findAll(pageable)

                /*
                 * Convert every Book entity
                 * into a BookResponse DTO.
                 */
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getPrice()
                ));
    }

    // DELETE
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}