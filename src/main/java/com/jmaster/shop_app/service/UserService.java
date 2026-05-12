package com.jmaster.shop_app.service;

import com.jmaster.shop_app.common.PageResponse;
import com.jmaster.shop_app.dto.request.UserRequest;
import com.jmaster.shop_app.dto.request.UserSearchRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    PageResponse<UserResponse> getUsers(UserSearchRequest userSearchRequest);

    UserResponse updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}
