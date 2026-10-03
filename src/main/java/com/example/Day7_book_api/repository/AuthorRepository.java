package com.example.Day7_book_api.repository;

import com.example.Day7_book_api.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    /*
     * We automatically get:
     *
     * save()
     * findAll()
     * findById()
     * deleteById()
     * existsById()
     * count()
     *
     * from JpaRepository.
     */
}