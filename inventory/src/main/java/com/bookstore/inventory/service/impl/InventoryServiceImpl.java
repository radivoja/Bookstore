package com.bookstore.inventory.service.impl;

import com.bookstore.inventory.dto.*;
import com.bookstore.inventory.entity.Inventory;
import com.bookstore.inventory.messaging.BookMessageProducer;
import com.bookstore.inventory.repository.InventoryRepository;
import com.bookstore.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final BookMessageProducer bookMessageProducer;

    @Override
    public int copies(Long id) {
        Optional<Inventory> book = inventoryRepository.findById(id);
        if(book.isPresent()){
            return book.get().getQuantity() - book.get().getReserved();
        }
        return 0;
    }

    @Override
    public Optional<Integer> add(Long id, int amount) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if(inventory.isPresent() && amount > 0){
            int newTotal = inventory.get().getQuantity() + amount;
            inventory.get().setQuantity(newTotal);
            inventoryRepository.save(inventory.get());
            return Optional.of(newTotal);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Integer> remove(Long id, int amount) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if(inventory.isPresent() && amount > 0){
            int newTotal = inventory.get().getQuantity() - amount;
            if(newTotal >= 0){
                inventory.get().setQuantity(newTotal);
                inventoryRepository.save(inventory.get());
                return Optional.of(newTotal);
            }
        }
        return Optional.empty();
    }

    @Override
    public void insertNewBook(BookMessageDto message) {
        Inventory inventory = Inventory.builder()
                .title(message.getTitle())
                .genre(message.getGenre())
                .author(message.getAuthor())
                .price(message.getPrice())
                .bookId(message.getId())
                .quantity(0)
                .reserved(0)
                .build();

        inventoryRepository.save(inventory);
    }

    @Override
    public boolean orderAccepted(OrderCreatedDto order) {
        // For each book ordered check if there enough copies
        for(BookDto book : order.getBooks()){
            int availableCopies = copies(book.getBookId());
            if(availableCopies < book.getQuantity()){
                OrderRejectedDto orderRejectedDto = new OrderRejectedDto();
                orderRejectedDto.setOrderId(order.getOrderId());
                orderRejectedDto.setReason("Not enough available copies for: " + book.getBookId());

                bookMessageProducer.sendOrderRejectedMessage(orderRejectedDto);
                return false;
            }
        }
        
        return true;
    }

    @Override
    public void reserveBooks(OrderCreatedDto order) {
        for(BookDto book : order.getBooks()){
            Optional<Inventory> inventory = inventoryRepository.findById(book.getBookId());
            if(inventory.isPresent()){
                inventory.get().setReserved(inventory.get().getReserved() + book.getQuantity());
                inventoryRepository.save(inventory.get());
            }
        }

        OrderAcceptedDto orderAcceptedDto = new OrderAcceptedDto();
        orderAcceptedDto.setOrderId(order.getOrderId());
        orderAcceptedDto.setBooks(order.getBooks());

        bookMessageProducer.sendOrderAcceptedMessage(orderAcceptedDto);
    }
}