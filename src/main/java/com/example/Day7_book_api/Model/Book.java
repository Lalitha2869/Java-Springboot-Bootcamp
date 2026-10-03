package com.example.Day7_book_api.Model;

//package com.example.book_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    // Primary key.
    // MySQL generates this automatically.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Book title.
    private String title;

    // Book price.
    private double price;

    // Many books can belong to one author.
    @ManyToOne

    // Creates author_id in the books table.
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties("books")
    private Author author;
}