package com.bookstore.catalog.messaging;

import com.bookstore.catalog.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(BookDto book){
        rabbitTemplate.convertAndSend("newBookAddedQueue", book);
    }
}
