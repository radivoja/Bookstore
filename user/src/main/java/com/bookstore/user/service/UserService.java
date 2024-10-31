package com.bookstore.user.service;


import com.bookstore.user.dto.UserDto;
import com.bookstore.user.dto.UserUpdateDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> getUsers();

    Optional<UserDto> getUserById(Long id);

    Optional<UserDto> updateUser(Long id, UserUpdateDto userDto);


}