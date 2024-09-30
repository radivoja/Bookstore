package com.bookstore.order.messaging;

import com.bookstore.order.dto.OrderAcceptedDto;
import com.bookstore.order.dto.OrderRejectedDto;
import com.bookstore.order.enums.Status;
import com.bookstore.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMessageConsumer {
    private final OrderService orderService;

    @RabbitListener(queues = "OrderAccepted")
    public void consumeOrderAcceptedMessage(OrderAcceptedDto message){
        orderService.updateStatus(message.getOrderId(), Status.ACCEPTED);
    }

    @RabbitListener(queues = "OrderRejected")
    public void consumeOrderRejectedMessage(OrderRejectedDto message){
        orderService.updateStatus(message.getOrderId(), Status.REJECTED);
    }
}
