package com.bookstore.order.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderCreatedDto {
    private Long orderId;
    private List<BookDto> books;
    private Long userId;
}
