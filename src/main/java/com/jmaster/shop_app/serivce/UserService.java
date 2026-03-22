package com.jmaster.shop_app.serivce;

import com.jmaster.shop_app.dto.request.UserRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(Long id, UserRequest userRequest);
    UserResponse getUserById(Long id);
    Page<UserResponse> getAllUsers(Pageable pageable);
    void deleteUser(Long id);
}
