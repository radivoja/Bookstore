package com.bookstore.order.service.implementation;

import com.bookstore.order.dto.OrderDto;
import com.bookstore.order.entity.Order;
import com.bookstore.order.enums.Status;
import com.bookstore.order.mappers.OrderMapper;
import com.bookstore.order.repository.OrderRepository;
import com.bookstore.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDto> getOrders() {
        System.out.println(orderRepository.findAll());
        return orderMapper.mapToDto(orderRepository.findAll());
    }

    @Override
    public Optional<OrderDto> getOrderById(Long id) {
        return orderRepository.findById(id).map(orderMapper::map);
    }

    @Override
    public Optional<OrderDto> createOrder(OrderDto orderDto) {
        Order order = orderMapper.map(orderDto);
        order.setStatus(Status.PENDING);
        orderRepository.save(order);
        return Optional.of(orderMapper.map(order));
    }

    @Override
    public Optional<OrderDto> updateOrder(Long id, OrderDto orderDto) {
            Optional<Order> order = orderRepository.findById(id);
            if(order.isPresent()) {
                Order updatedOrder = orderMapper.map(orderDto);
                updatedOrder.setCreated(order.get().getCreated());
                updatedOrder.setId(id);
                orderRepository.save(updatedOrder);
                return Optional.ofNullable(orderMapper.map(order.get()));
            }
            return Optional.empty();
    }

    @Override
    public Optional<OrderDto> deleteOrder(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            orderRepository.deleteById(id);
            return Optional.of(new OrderDto());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void updateStatus(Long id, Status status) {
        Optional<Order> order = orderRepository.findById(id);
        if(order.isPresent()){
            order.get().setStatus(status);
            orderRepository.save(order.get());
        }
    }
}
