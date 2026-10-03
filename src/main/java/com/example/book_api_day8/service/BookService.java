package com.example.book_api_day8.service;

import com.example.book_api_day8.dto.BookRequest;
import com.example.book_api_day8.dto.BookResponse;
import com.example.book_api_day8.model.Book;
import com.example.book_api_day8.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

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
    public Optional<BookResponse> getBookById(Long id) {

        return bookRepository.findById(id)
                .map(book -> new BookResponse(
                        book.getId(),
                        book.getTitle(),
                        book.getPrice()
                ));
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

    // DELETE
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}