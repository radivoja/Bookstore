package com.bookstore.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookMessageDto {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Double price;
}