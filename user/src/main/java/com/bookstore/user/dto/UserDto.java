package com.bookstore.user.dto;

import com.bookstore.user.model.Role;
import lombok.Data;
@Data
public class UserDto {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private Role role;
}
