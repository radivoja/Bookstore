package com.bookstore.inventory.controller;


import com.bookstore.inventory.dto.BookQuantityChangedDto;
import com.bookstore.inventory.messaging.BookMessageProducer;
import com.bookstore.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookstore/books")
public class InventoryController {
    private final InventoryService inventoryService;
    private final BookMessageProducer bookMessageProducer;

    @GetMapping("/{bookId}/copies")
    public ResponseEntity<Integer> copies(@PathVariable Long bookId) {
        return ResponseEntity.ok(inventoryService.copies(bookId));
    }

    @PostMapping("/{bookId}/add")
    public ResponseEntity<?> add(@PathVariable Long bookId, @RequestBody Integer amount) {
        Optional<Integer> total = inventoryService.add(bookId, amount);
        if(total.isPresent() && amount > 0) {
            BookQuantityChangedDto bookQuantityChangedDto = BookQuantityChangedDto.builder()
                    .bookId(bookId)
                    .totalCount(total.get())
                    .build();
            bookMessageProducer.sendMessage(bookQuantityChangedDto);

            return ResponseEntity.ok(bookQuantityChangedDto);
        }
        return ResponseEntity.badRequest().body("Non existing book with id: " + bookId + " or amount: " + amount + ", not valid");
    }

    @PostMapping("/{bookId}/remove")
    public ResponseEntity<?> remove(@PathVariable Long bookId, @RequestBody Integer amount) {
        Optional<Integer> total = inventoryService.remove(bookId, amount);
        if(total.isPresent() && amount > 0){
            BookQuantityChangedDto bookQuantityChangedDto = BookQuantityChangedDto.builder()
                    .bookId(bookId)
                    .totalCount(total.get())
                    .build();
            bookMessageProducer.sendMessage(bookQuantityChangedDto);

            return ResponseEntity.ok(bookQuantityChangedDto);
        }
        return ResponseEntity.badRequest().body("Non existing book with id: " + bookId + " or amount: " + amount + ", not valid");
    }
}
