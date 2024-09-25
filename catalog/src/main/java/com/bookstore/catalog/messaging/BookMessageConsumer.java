package com.bookstore.catalog.messaging;

import com.bookstore.catalog.dto.BookQuantityChangedDto;
import com.bookstore.catalog.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMessageConsumer {
    private final BookService bookService;

    @RabbitListener(queues = "BookQuantityChanged")
    public void consumeMessage(BookQuantityChangedDto message){
        bookService.updateStatus(message);
    }
}
