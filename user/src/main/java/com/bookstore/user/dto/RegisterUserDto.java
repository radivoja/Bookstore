package com.bookstore.user.dto;

import com.bookstore.user.model.Role;
import lombok.Data;

@Data
public class RegisterUserDto {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private Role role;
}