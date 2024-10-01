package com.bookstore.notification.messaging;

import com.bookstore.notification.dto.OrderConfirmed;
import com.bookstore.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMessageConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = "OrderConfirmed")
    public void consumeOrderConfirmedMessage(OrderConfirmed message){
        notificationService.sendEmail(message.getOrderId());
    }
}
