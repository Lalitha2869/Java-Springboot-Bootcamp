package com.example.book_api_day8.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponse {

    private Long id;

    private String title;

    private double price;
}
//This represents what the API is allowed to return.