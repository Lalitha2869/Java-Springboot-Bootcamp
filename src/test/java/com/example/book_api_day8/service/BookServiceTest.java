package com.example.book_api_day8.service;




import com.example.book_api_day8.dto.BookRequest;
import com.example.book_api_day8.dto.BookResponse;
import com.example.book_api_day8.exception.ResourceNotFoundException;
import com.example.book_api_day8.model.Book;
import com.example.book_api_day8.repository.BookRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;


    @Test
    void shouldCreateBook() {

        // Arrange
        BookRequest request = new BookRequest();
        request.setTitle("Java Basics");
        request.setPrice(500);

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle("Java Basics");
        savedBook.setPrice(500);

        when(bookRepository.save(any(Book.class)))
                .thenReturn(savedBook);

        // Act
        BookResponse response =
                bookService.createBook(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Java Basics", response.getTitle());
        assertEquals(500, response.getPrice());

        verify(bookRepository).save(any(Book.class));
    }


    @Test
    void shouldFindBookById() {

        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Spring Boot");
        book.setPrice(750);

        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));

        // Act
        BookResponse response =
                bookService.getBookById(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Spring Boot", response.getTitle());
        assertEquals(750, response.getPrice());

        verify(bookRepository).findById(1L);
    }


    @Test
    void shouldThrowExceptionWhenBookNotFound() {

        // Arrange
        when(bookRepository.findById(999L))
                .thenReturn(Optional.empty());

        // Act + Assert
        ResourceNotFoundException exception =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> bookService.getBookById(999L)
                );

        assertEquals(
                "Book with id 999 not found",
                exception.getMessage()
        );

        verify(bookRepository).findById(999L);
    }
}