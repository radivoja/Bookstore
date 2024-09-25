package com.bookstore.inventory.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Inventory {
    @Id
    private Long bookId;
    private String title;
    private String author;
    private String genre;
    private Double price;
    private Integer quantity;
    private Integer reserved;
}
