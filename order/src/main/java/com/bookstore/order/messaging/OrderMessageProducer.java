package com.bookstore.order.messaging;

import com.bookstore.order.dto.OrderCreatedDto;
import com.bookstore.order.dto.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(OrderDto order){
        OrderCreatedDto message = OrderCreatedDto
                .builder()
                .orderId(order.getId())
                .books(order.getBooks())
                .userId(order.getUserId())
                .build();

        rabbitTemplate.convertAndSend("OrderCreated", message);
    }
}
