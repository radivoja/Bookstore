package com.bookstore.inventory.messaging;

import com.bookstore.inventory.dto.BookMessageDto;
import com.bookstore.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMessageConsumer {
    private final InventoryService inventoryService;

    @RabbitListener(queues = "newBookAddedQueue")
    public void consumeMessage(BookMessageDto message){
        inventoryService.insertNewBook(message);
    }
}
