package com.bookstore.order.controller;

import com.bookstore.order.dto.OrderDto;
import com.bookstore.order.messaging.OrderMessageProducer;
import com.bookstore.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/bookstore/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMessageProducer orderMessageProducer;

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.of(orderService.getOrderById(id));
    }


    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody OrderDto order) {
        Optional<OrderDto> orderDto = orderService.createOrder(order);
        if (orderDto.isPresent()) {
            orderMessageProducer.sendMessage(orderDto.get());
            return ResponseEntity.status(HttpStatus.CREATED).body("Successfully created");
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Cannot be created");
    }


    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, @RequestBody OrderDto body) {
        return ResponseEntity.of(orderService.updateOrder(id, body));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        if(orderService.deleteOrder(id).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found with "+ id);
        }
    }
}
