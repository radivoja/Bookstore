package com.bookstore.inventory.service;


import com.bookstore.inventory.dto.BookMessageDto;

public interface InventoryService {

    int copies(Long id);

    int add(Long id, int amount);

    int remove(Long id, int amount);

    void insertNewBook(BookMessageDto message);

}
