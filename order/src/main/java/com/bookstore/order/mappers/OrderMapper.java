package com.bookstore.order.mappers;

import com.bookstore.order.dto.OrderDto;
import com.bookstore.order.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = BookMapper.class)
public interface OrderMapper {
    OrderDto map(Order order);

    Order map(OrderDto bookDto);

    List<OrderDto> mapToDto(List<Order> orders);

    List<Order> mapToEntity(List<OrderDto> ordersDto);

}
