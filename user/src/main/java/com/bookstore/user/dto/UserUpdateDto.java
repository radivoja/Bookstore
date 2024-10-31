package com.bookstore.user.dto;

import com.bookstore.user.model.Role;
import lombok.Data;

@Data
public class UserUpdateDto {
   private String fullName;
   private String email;
   private Role role;
}
