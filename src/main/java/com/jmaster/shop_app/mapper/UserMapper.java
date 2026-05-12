package com.jmaster.shop_app.mapper;

import com.jmaster.shop_app.dto.request.UserRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import com.jmaster.shop_app.entity.UserEntity;
import com.jmaster.shop_app.enums.UserStatus;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserRequest request, String passwordHash) {
        if (request == null) {
            return null;
        }

        return UserEntity.builder()
                .username(request.getUsername())
                .password(passwordHash)
                .fullName(request.getFullName())
                .email(request.getEmail())
                .status(toUserStatus(request.getStatus()))
                .build();
    }

    public void updateEntity(UserEntity user, UserRequest request) {
        if (user == null || request == null) {
            return;
        }

        user.setUsername(request.getUsername());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        if (request.getStatus() != null && !request.getStatus().isBlank()) {
            user.setStatus(UserStatus.fromKey(request.getStatus()));
        }
    }


    public UserResponse toResponse(UserEntity user) {
        if (user == null) {
            return null;
        }

        UserStatus status = user.getStatus();

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .status(status != null ? status.getKey() : null)
                .statusName(status != null ? status.getValue() : null)
                .createdAt(user.getCreatedAt())
                .build();
    }

    private UserStatus toUserStatus(String status) {
        if (status == null || status.isBlank()) {
            return UserStatus.ACTIVE;
        }

        return UserStatus.fromKey(status);
    }
}