package com.jmaster.shop_app.serivce.impl;

import com.jmaster.shop_app.dto.request.UserRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import com.jmaster.shop_app.entity.UserEntity;
import com.jmaster.shop_app.mapper.UserMapper;
import com.jmaster.shop_app.repository.UserRepository;
import com.jmaster.shop_app.serivce.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        // Kiểm tra username đã tồn tại
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username đã tồn tại");
        }

        // Kiểm tra email đã tồn tại
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email đã tồn tại");
        }

        UserEntity user = UserEntity.builder()
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .fullName(userRequest.getFullName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
                .build();

        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));

        // Kiểm tra username nếu thay đổi
        if (!user.getUsername().equals(userRequest.getUsername())) {
            if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
                throw new RuntimeException("Username đã tồn tại");
            }
            user.setUsername(userRequest.getUsername());
        }

        // Kiểm tra email nếu thay đổi
        if (!user.getEmail().equals(userRequest.getEmail())) {
            if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
                throw new RuntimeException("Email đã tồn tại");
            }
            user.setEmail(userRequest.getEmail());
        }

        // Cập nhật password nếu có
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        user.setFullName(userRequest.getFullName());
        user.setPhone(userRequest.getPhone());
        user.setAddress(userRequest.getAddress());

        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        return userMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        Page<UserEntity> users = userRepository.findAll(pageable);
        return users.map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User không tồn tại");
        }
        userRepository.deleteById(id);
    }
}
