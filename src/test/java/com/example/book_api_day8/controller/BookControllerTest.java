//package com.example.book_api_day8.controller;
//
//import com.example.book_api_day8.dto.BookResponse;
//import com.example.book_api_day8.service.BookService;
//
//import org.junit.jupiter.api.Test;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(BookController.class)
//class BookControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private BookService bookService;
//
//    @Test
//    void shouldGetBookById() throws Exception {
//
//        BookResponse bookResponse =
//                new BookResponse(
//                        1L,
//                        "Spring Boot",
//                        750
//                );
//
//        when(bookService.getBookById(1L))
//                .thenReturn(bookResponse);
//
//        mockMvc.perform(
//                        get("/api/books/1")
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.title").value("Spring Boot"))
//                .andExpect(jsonPath("$.price").value(750));
//    }
//}