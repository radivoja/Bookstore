package com.bookstore.catalog.mappers;

import com.bookstore.catalog.dto.BookDto;
import com.bookstore.catalog.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto map(Book book);

    Book map(BookDto bookDto);

    List<BookDto> mapToDto(List<Book> books);

    List<Book> mapToEntity(List<BookDto> booksDto);

}
