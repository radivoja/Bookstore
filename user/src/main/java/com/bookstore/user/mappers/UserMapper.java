package com.bookstore.user.mappers;

import com.bookstore.user.dto.UserDto;
import com.bookstore.user.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(User user);

    User map(UserDto userDto);

    List<UserDto> mapToDto(List<User> users);

    List<User> mapToEntity(List<UserDto> usersDto);

}
