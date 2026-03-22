package com.jmaster.shop_app.serivce.impl;

import com.jmaster.shop_app.dto.request.LoginRequest;
import com.jmaster.shop_app.dto.request.RegisterRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import com.jmaster.shop_app.entity.RoleEntity;
import com.jmaster.shop_app.entity.UserEntity;
import com.jmaster.shop_app.mapper.UserMapper;
import com.jmaster.shop_app.repository.RoleRepository;
import com.jmaster.shop_app.repository.UserRepository;
import com.jmaster.shop_app.security.JwtUtil;
import com.jmaster.shop_app.serivce.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;

    @Override
    public String login(LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity user = userMapper.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        RoleEntity role = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(role));

        userRepository.save(user);
        return userMapper.toResponse(user);
    }
}
