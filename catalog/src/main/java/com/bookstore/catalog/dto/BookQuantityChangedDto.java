package com.bookstore.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookQuantityChangedDto {
    private Long bookId;
    private Integer totalCount;
}
