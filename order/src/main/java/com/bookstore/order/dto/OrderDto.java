package com.bookstore.order.dto;

import com.bookstore.order.enums.Status;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private List<BookDto> books;
    private Timestamp created;
    private Timestamp updated;
    private Status status;
}
