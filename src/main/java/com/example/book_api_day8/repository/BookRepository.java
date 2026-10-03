package com.example.book_api_day8.repository;



import com.example.book_api_day8.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}