package com.bookstore.catalog.controller;

import com.bookstore.catalog.dto.BookDto;
import com.bookstore.catalog.messaging.BookMessageProducer;
import com.bookstore.catalog.service.BookSearchService;
import com.bookstore.catalog.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookstore/books")
public class BookController{
    private final BookService bookService;
    private final BookSearchService bookSearchService;
    private final BookMessageProducer bookMessageProducer;

    @GetMapping()
    public ResponseEntity<List<BookDto>> getBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.of(bookService.getBookById(id));
    }

    @PostMapping()
    public ResponseEntity<String> createBook(@RequestBody BookDto body) {
        Optional<BookDto> bookDto = bookService.createBook(body);
        if (bookDto.isPresent()) {
            bookMessageProducer.sendMessage(bookDto.get());
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Cannot be created");
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto body) {
        return ResponseEntity.of(bookService.updateBook(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Long id) {
        if(bookService.deleteBook(id).isPresent()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found with "+ id);
        }
    }

    public ResponseEntity<List<BookDto>> search(String title, String author, String genre, Double price) {
        return ResponseEntity.ok(bookSearchService.search(title, author, genre, price));
    }
}
