package com.bookstore.user.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String token;
    private Long expiresIn;
}
