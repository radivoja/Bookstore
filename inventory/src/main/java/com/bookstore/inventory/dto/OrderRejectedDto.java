package com.bookstore.inventory.dto;

import lombok.Data;

@Data
public class OrderRejectedDto {
    private Long orderId;
    private String reason;
}
