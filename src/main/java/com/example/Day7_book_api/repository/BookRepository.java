package com.example.Day7_book_api.repository;


import com.example.Day7_book_api.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // Find books whose title contains the given text.
    List<Book> findByTitleContaining(String title);

    // Find books by author name.
    List<Book> findByAuthorName(String authorName);

    // Custom JPQL query.
    @Query("SELECT b FROM Book b WHERE b.price > :price")
    List<Book> findBooksAbovePrice(@Param("price") double price);
}