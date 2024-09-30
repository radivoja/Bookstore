package com.bookstore.order.service;

import com.bookstore.order.dto.OrderDto;
import com.bookstore.order.enums.Status;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<OrderDto> getOrders();

    Optional<OrderDto> getOrderById(Long id);

    Optional<OrderDto> createOrder(OrderDto order);

    Optional<OrderDto> updateOrder(Long id, OrderDto order);

    Optional<OrderDto> deleteOrder(Long id);

    void updateStatus(Long id, Status status);
}
