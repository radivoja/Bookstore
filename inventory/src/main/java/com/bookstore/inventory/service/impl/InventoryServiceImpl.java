package com.bookstore.inventory.service.impl;

import com.bookstore.inventory.dto.BookMessageDto;
import com.bookstore.inventory.entity.Book;
import com.bookstore.inventory.repository.BookRepository;
import com.bookstore.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final BookRepository bookRepository;


    @Override
    public int copies(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            return book.get().getQuantity();
        }
        return 0;
    }

    @Override
    public int add(Long id, int amount) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent() && amount > 0){
            int newTotal = book.get().getQuantity() + amount;
            book.get().setQuantity(newTotal);
            return newTotal;
        }
        return 0;
    }

    @Override
    public int remove(Long id, int amount) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent() && amount > 0){
            int newTotal = book.get().getQuantity() - amount;
            if(newTotal > 0){
                book.get().setQuantity(newTotal);
            }
            return newTotal;
        }
        return 0;
    }

    @Override
    public void insertNewBook(BookMessageDto message) {
        Book book = Book.builder()
                .title(message.getTitle())
                .genre(message.getGenre())
                .author(message.getAuthor())
                .price(message.getPrice())
                .bookId(message.getId())
                .quantity(0)
                .build();

        bookRepository.save(book);

    }
}
