package com.example.Day7_book_api.Service;



import com.example.Day7_book_api.Model.Author;
import com.example.Day7_book_api.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;


    // Constructor injection.
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    // ---------------------------------------------------------
    // GET ALL AUTHORS
    // ---------------------------------------------------------

    public List<Author> getAllAuthors() {

        return authorRepository.findAll();
    }


    // ---------------------------------------------------------
    // GET AUTHOR BY ID
    // ---------------------------------------------------------

    public Optional<Author> getAuthorById(Long id) {

        return authorRepository.findById(id);
    }


    // ---------------------------------------------------------
    // CREATE / UPDATE AUTHOR
    // ---------------------------------------------------------

    public Author saveAuthor(Author author) {

        return authorRepository.save(author);
    }


    // ---------------------------------------------------------
    // DELETE AUTHOR
    // ---------------------------------------------------------

    public void deleteAuthor(Long id) {

        authorRepository.deleteById(id);
    }
}