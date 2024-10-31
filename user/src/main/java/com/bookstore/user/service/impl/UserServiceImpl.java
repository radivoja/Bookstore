package com.bookstore.user.service.impl;


import com.bookstore.user.dto.UserDto;
import com.bookstore.user.dto.UserUpdateDto;
import com.bookstore.user.mappers.UserMapper;
import com.bookstore.user.model.User;
import com.bookstore.user.repository.UserRepository;
import com.bookstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getUsers() {
        return userMapper.mapToDto(userRepository.findAll());
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(userMapper::map);
    }

    @Override
    public Optional<UserDto> updateUser(Long id, UserUpdateDto userDto) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            if(userDto.getFullName() != null && !userDto.getFullName().isBlank()){
                user.get().setFullName(userDto.getFullName());
            }

            if(userDto.getEmail() != null && !userDto.getEmail().isBlank()){
                user.get().setEmail(userDto.getEmail());
            }

            if(userDto.getRole() != null && !userDto.getRole().name().isBlank()){
                user.get().setRole(userDto.getRole());
            }

            userRepository.save(user.get());

            return Optional.of(userMapper.map(user.get()));

        }
        return Optional.empty();
    }


}