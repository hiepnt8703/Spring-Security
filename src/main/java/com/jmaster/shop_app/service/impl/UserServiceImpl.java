package com.jmaster.shop_app.service.impl;

import com.jmaster.shop_app.common.PageResponse;
import com.jmaster.shop_app.dto.request.UserRequest;
import com.jmaster.shop_app.dto.request.UserSearchRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import com.jmaster.shop_app.entity.UserEntity;
import com.jmaster.shop_app.exception.ConflictException;
import com.jmaster.shop_app.exception.ResourceNotFoundException;
import com.jmaster.shop_app.mapper.UserMapper;
import com.jmaster.shop_app.repository.UserRepository;
import com.jmaster.shop_app.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;


    @Override
    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByUsernameAndDeletedFalse(userRequest.getUsername())) {
            throw new ConflictException("Username đã tồn tại");
        }

        if (userRequest.getEmail() != null
                && !userRequest.getEmail().isBlank()
                && userRepository.existsByEmailAndDeletedFalse(userRequest.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }


        String passwordHash = passwordEncoder.encode(userRequest.getPassword());
        UserEntity user = userMapper.toEntity(userRequest, passwordHash);
        UserEntity savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        if (Boolean.TRUE.equals(user.getDeleted())) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng");
        }

        return userMapper.toResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getUsers(UserSearchRequest userSearchRequest) {

        if (userSearchRequest == null) {
            userSearchRequest = new UserSearchRequest();
        }

        int page = userSearchRequest.getPage() == null ? 0 : userSearchRequest.getPage();
        int size = userSearchRequest.getSize() == null ? 10 : userSearchRequest.getSize();
        String sortBy = userSearchRequest.getSortBy() == null || userSearchRequest.getSortBy().isBlank()
                ? "id"
                : userSearchRequest.getSortBy();
        String sortDir = userSearchRequest.getSortDir() == null || userSearchRequest.getSortDir().isBlank()
                ? "desc"
                : userSearchRequest.getSortDir();

        boolean includeDeleted = Boolean.TRUE.equals(userSearchRequest.getIncludeDeleted());

        if (page < 0) {
            throw new IllegalArgumentException("Page phải >= 0");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Size phải > 0");
        }

        if (size > 100) {
            throw new IllegalArgumentException("Size không được vượt quá 100");
        }

        Sort sort = Sort.by(sortBy);

        if ("desc".equalsIgnoreCase(sortDir)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserEntity> users = includeDeleted
                ? userRepository.findAll(pageable)
                : userRepository.findByDeletedFalse(pageable);

        Page<UserResponse> userResponses = users.map(userMapper::toResponse);

        return PageResponse.from(userResponses);
    }

    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        if (Boolean.TRUE.equals(user.getDeleted())) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng");
        }

        if (userRepository.existsByUsernameAndIdNotAndDeletedFalse(userRequest.getUsername(), id)) {
            throw new ConflictException("Username đã tồn tại");
        }

        if (userRequest.getEmail() != null
                && !userRequest.getEmail().isBlank()
                && userRepository.existsByEmailAndIdNotAndDeletedFalse(userRequest.getEmail(), id)) {
            throw new ConflictException("Email đã tồn tại");
        }

        userMapper.updateEntity(user, userRequest);

        if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        UserEntity savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        if (Boolean.TRUE.equals(user.getDeleted())) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng");
        }

        user.setDeleted(true);

        userRepository.save(user);
    }


}
