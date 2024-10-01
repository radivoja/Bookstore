package com.bookstore.notification.service.impl;

import com.bookstore.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendEmail(Long id) {
        System.out.println("Message from notification received: " + id);
    }
}