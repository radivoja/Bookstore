package com.bookstore.user.service.impl;

import com.bookstore.user.dto.LoginUserDto;
import com.bookstore.user.dto.RegisterUserDto;
import com.bookstore.user.dto.TokenDto;
import com.bookstore.user.dto.UserDto;
import com.bookstore.user.jwtutil.JwlUtil;
import com.bookstore.user.mappers.UserMapper;
import com.bookstore.user.model.User;
import com.bookstore.user.repository.UserRepository;
import com.bookstore.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwlUtil jwlUtil;

    @Override
    public UserDto register(RegisterUserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getFullName());
        user.setRole(userDto.getRole());
        userRepository.save(user);
        return userMapper.map(user);
    }

    @Override
    public TokenDto login(LoginUserDto user) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        System.out.println(authenticate);
        if (authenticate.isAuthenticated()){
            TokenDto tokenDto = new TokenDto();
            String token = jwlUtil.generateToken(user.getUsername());
            tokenDto.setToken(token);
            return tokenDto;
        }

        return null;
    }
}
