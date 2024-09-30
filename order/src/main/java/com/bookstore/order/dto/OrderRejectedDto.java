package com.bookstore.order.dto;

import lombok.Data;

@Data
public class OrderRejectedDto {
    private Long orderId;
    private String reason;
}
