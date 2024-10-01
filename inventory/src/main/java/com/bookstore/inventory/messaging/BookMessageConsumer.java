package com.bookstore.inventory.messaging;

import com.bookstore.inventory.dto.BookMessageDto;
import com.bookstore.inventory.dto.OrderConfirmed;
import com.bookstore.inventory.dto.OrderCreatedDto;
import com.bookstore.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMessageConsumer {
    private final InventoryService inventoryService;

    @RabbitListener(queues = "NewBookAdded")
    public void consumeNewBookAddedMessage(BookMessageDto message){
        inventoryService.insertNewBook(message);
    }

    @RabbitListener(queues = "OrderCreated")
    public void consumeOrderCreatedMessage(OrderCreatedDto message){
        if(inventoryService.orderAccepted(message)){
            inventoryService.reserveBooks(message);
        }
    }

    @RabbitListener(queues = "OrderConfirmed")
    public void consumeOrderConfirmedMessage(OrderConfirmed message){
        inventoryService.removeReservation(message.getOrderId());
    }
}
