package com.bookstore.inventory.messaging;

import com.bookstore.inventory.dto.BookQuantityChangedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(BookQuantityChangedDto message){
        rabbitTemplate.convertAndSend("BookQuantityChanged", message);
    }
}
