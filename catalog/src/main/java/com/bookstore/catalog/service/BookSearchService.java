package com.bookstore.catalog.service;


import com.bookstore.catalog.dto.BookDto;

import java.util.List;

public interface BookSearchService {
    List<BookDto> search(String title, String author, String genre, Double price);
}
