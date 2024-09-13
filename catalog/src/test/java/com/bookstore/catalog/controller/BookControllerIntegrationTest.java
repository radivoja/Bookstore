package com.bookstore.catalog.controller;

import com.bookstore.catalog.dto.BookDto;
import com.bookstore.catalog.entity.Book;
import com.bookstore.catalog.service.BookService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BookControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookService bookService;

    public static BookDto mastery(){
        return  BookDto.builder()
                .title("Mastery")
                .author("George Leonard")
                .genre("Self-Improvement & Inspiration")
                .price(1500.00)
                .build();
    }

    @Test
    public void testGetAll() throws Exception {
        bookService.createBook(mastery());
        ResultActions result = mockMvc.perform(get("/bookstore/books"));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testGetById() throws Exception {
        Optional<BookDto> mastery = bookService.createBook(mastery());
        ResultActions result = mockMvc.perform(get("/bookstore/books/{id}", mastery.get().getId()));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(mastery.get().getId()))
                .andExpect(jsonPath("$.title").value("Mastery"))
                .andExpect(jsonPath("$.author").value("George Lenard"))
                .andExpect(jsonPath("$.genre").value("Self-Improvement & Inspiration"))
                .andExpect(jsonPath("$.prize").value("1500.00"));
    }

    @Test
    public void testCreate() throws Exception {
        String bookJson = "{\"title\":\"Mastery#1\",\"author\":\"George Lenard\",\"genre\":\"Self-Improvement & Inspiration\",\"price\":\"1500.00\"}";
        ResultActions result = mockMvc.perform(post("/bookstore/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson));

        result.andExpect(status().isCreated());
    }

    @Test
    public void testUpdate() throws Exception {
        Optional<BookDto> mastery = bookService.createBook(mastery());
        String bookJson = "{\"title\":\"Mastery Vol.2\",\"author\":\"George Lenard\",\"genre\":\"Self-Improvement & Inspiration\",\"price\":\"2500.00\"}";

        ResultActions result = mockMvc.perform(put("/bookstore/books/{id}", mastery.get().getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJson));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mastery.get().getId()))
                .andExpect(jsonPath("$.title").value("Mastery Vol.2"))
                .andExpect(jsonPath("$.author").value("George Lenard"))
                .andExpect(jsonPath("$.genre").value("Self-Improvement & Inspiration"))
                .andExpect(jsonPath("$.prize").value("2500.00"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Optional<BookDto> mastery = bookService.createBook(mastery());
        ResultActions result = mockMvc.perform(delete("/bookstore/books/{id}", mastery.get().getId()));

        result.andExpect(status().isNoContent())
                .andExpect(content().string("Successfully deleted"));
    }
}
