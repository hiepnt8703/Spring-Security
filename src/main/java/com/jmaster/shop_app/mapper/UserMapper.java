package com.jmaster.shop_app.mapper;

import com.jmaster.shop_app.dto.request.RegisterRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import com.jmaster.shop_app.entity.RoleEntity;
import com.jmaster.shop_app.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity toEntity(RegisterRequest request);

    @Mapping(target = "role", expression = "java(mapRole(user))")
    UserResponse toResponse(UserEntity user);

    default String mapRole(UserEntity user) {
        return user.getRoles().stream()
                .findFirst()
                .map(RoleEntity::getName)
                .orElse(null);
    }
}
