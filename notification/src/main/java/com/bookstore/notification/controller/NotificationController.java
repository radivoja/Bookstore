package com.bookstore.notification.controller;

import com.bookstore.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bookstore/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping()
    public ResponseEntity<String> sendSms(){
        return ResponseEntity.ok("Test SMS");
    }
}
