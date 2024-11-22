package com.bookstore.user.controller;

import com.bookstore.user.dto.LoginUserDto;
import com.bookstore.user.dto.RegisterUserDto;
import com.bookstore.user.dto.UserDto;
import com.bookstore.user.service.AuthService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/bookstore/auth")
@RestController
@Data
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody RegisterUserDto user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto request) {
        return ResponseEntity.ok(authService.login(request).getToken());
    }

}