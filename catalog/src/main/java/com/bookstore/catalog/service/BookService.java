package com.bookstore.catalog.service;

import com.bookstore.catalog.dto.BookDto;
import com.bookstore.catalog.dto.BookQuantityChangedDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getBooks();

    Optional<BookDto> getBookById(Long id);

    Optional<BookDto> createBook(BookDto bookDto);

    Optional<BookDto> updateBook(Long id, BookDto bookDto);

    Optional<BookDto> deleteBook(Long id);

    void updateStatus(BookQuantityChangedDto bookQuantityChangedDto);
}
