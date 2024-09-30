package com.bookstore.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderAcceptedDto {
    private Long orderId;
    private List<BookDto> books;
}
