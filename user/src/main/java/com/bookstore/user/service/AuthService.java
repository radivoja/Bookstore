package com.bookstore.user.service;

import com.bookstore.user.dto.LoginUserDto;
import com.bookstore.user.dto.RegisterUserDto;
import com.bookstore.user.dto.TokenDto;
import com.bookstore.user.dto.UserDto;

public interface AuthService {

    UserDto register(RegisterUserDto user);

    TokenDto login(LoginUserDto user);

}
