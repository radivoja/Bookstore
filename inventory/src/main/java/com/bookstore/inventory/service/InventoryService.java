package com.bookstore.inventory.service;


import com.bookstore.inventory.dto.BookMessageDto;

import java.util.Optional;

public interface InventoryService {

    int copies(Long id);

    Optional<Integer> add(Long id, int amount);

    Optional<Integer> remove(Long id, int amount);

    void insertNewBook(BookMessageDto message);

}
