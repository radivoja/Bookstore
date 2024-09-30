package com.bookstore.order.mappers;

import com.bookstore.order.dto.BookDto;
import com.bookstore.order.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto map(Book book);

    Book map(BookDto bookDto);

    List<BookDto> mapToDto(List<Book> books);

    List<Book> mapToEntity(List<BookDto> booksDto);

}
