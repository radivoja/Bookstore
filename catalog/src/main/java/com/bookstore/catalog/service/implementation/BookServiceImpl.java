package com.bookstore.catalog.service.implementation;

import com.bookstore.catalog.dto.BookDto;
import com.bookstore.catalog.dto.BookQuantityChangedDto;
import com.bookstore.catalog.enums.Status;
import com.bookstore.catalog.entity.Book;
import com.bookstore.catalog.mappers.BookMapper;
import com.bookstore.catalog.repository.BookRepository;
import com.bookstore.catalog.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> getBooks() {
        return bookMapper.mapToDto(bookRepository.findAll());
    }

    @Override
    public Optional<BookDto> getBookById(Long id) {
        return bookRepository.findById(id).map(bookMapper::map);
    }

    @Override
    public Optional<BookDto> createBook(BookDto bookDto) {
        Book book = bookMapper.map(bookDto);
        bookRepository.save(book);
        return Optional.of(bookMapper.map(book));
    }

    @Override
    public Optional<BookDto> updateBook(Long id, BookDto bookDto) {
        if(bookRepository.existsById(id)) {
            bookDto.setId(id);
            return Optional.ofNullable(bookMapper.map(bookRepository.save(
                    bookMapper.map(bookDto))));
        }
        return Optional.empty();
    }

    @Override
    public Optional<BookDto> deleteBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return Optional.of(new BookDto());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void updateStatus(BookQuantityChangedDto bookQuantityChangedDto) {
        Optional<Book> book = bookRepository.findById(bookQuantityChangedDto.getBookId());
        if(book.isPresent()){
            if(bookQuantityChangedDto.getTotalCount() > 0){
                if(!book.get().getStatus().equals(Status.IN_STOCK)){
                    book.get().setStatus(Status.IN_STOCK);
                    bookRepository.save(book.get());
                }
            }
            else{
                if(!book.get().getStatus().equals(Status.OUT_OF_STOCK)){
                    book.get().setStatus(Status.OUT_OF_STOCK);
                    bookRepository.save(book.get());
                }
            }
        }
    }
}
