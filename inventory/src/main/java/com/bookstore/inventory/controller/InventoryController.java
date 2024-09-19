package com.bookstore.inventory.controller;


import com.bookstore.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookstore/books")
public class InventoryController {
    private final InventoryService bookService;

    @GetMapping("/{bookId}/copies")
    public ResponseEntity<Integer> copies(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookService.copies(bookId));
    }

    @PostMapping("/{bookId}/add")
    public ResponseEntity<Integer> add(@PathVariable Long bookId, @RequestBody Integer amount) {
        return ResponseEntity.ok(bookService.add(bookId, amount));
    }

    @PostMapping("/{bookId}/remove")
    public ResponseEntity<Integer> remove(@PathVariable Long bookId, @RequestBody Integer amount) {
        return ResponseEntity.ok(bookService.remove(bookId, amount));
    }
}
