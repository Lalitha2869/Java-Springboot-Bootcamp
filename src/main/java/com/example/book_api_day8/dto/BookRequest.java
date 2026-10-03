package com.example.book_api_day8.dto;



//import lombok.Data;
//
//@Data
//public class BookRequest {
//
//    private String title;
//
//    private double price;
//}
//What does this represent?
//
//It represents what the client is allowed to send when creating a book.

//Spring converts that JSON into:
//
//BookRequest object



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(
            min = 2,
            max = 100,
            message = "Title must be between 2 and 100 characters"
    )
    private String title;

    @Positive(message = "Price must be greater than 0")
    private double price;
}