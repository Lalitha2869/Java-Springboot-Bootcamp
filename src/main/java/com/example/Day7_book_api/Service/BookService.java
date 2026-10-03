package com.example.Day7_book_api.Service;

import com.example.Day7_book_api.Model.Book;
import com.example.Day7_book_api.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    // Spring injects BookRepository.
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all books.
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Get one book.
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Create or update a book.
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    // Delete a book.
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    // Search by title.
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContaining(title);
    }

    // Search by author.
    public List<Book> searchByAuthor(String authorName) {
        return bookRepository.findByAuthorName(authorName);
    }

    // Custom price query.
    public List<Book> findBooksAbovePrice(double price) {
        return bookRepository.findBooksAbovePrice(price);
    }
}