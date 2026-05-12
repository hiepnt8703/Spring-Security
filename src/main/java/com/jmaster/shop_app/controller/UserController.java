package com.jmaster.shop_app.controller;

import com.jmaster.shop_app.common.ApiResponse;
import com.jmaster.shop_app.common.PageResponse;
import com.jmaster.shop_app.dto.request.UserRequest;
import com.jmaster.shop_app.dto.request.UserSearchRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import com.jmaster.shop_app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @Valid @RequestBody UserRequest request
    ) {
        UserResponse userResponse = userService.createUser(request);

        return ApiResponse.success(
                201,
                "Tạo người dùng thành công",
                userResponse
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);

        return ApiResponse.success(
                "Lấy thông tin người dùng thành công",
                userResponse
        );
    }

    @PostMapping("/search")
    public ApiResponse<PageResponse<UserResponse>> getUsers(
            @RequestBody UserSearchRequest userSearchRequest
            ) {
        PageResponse<UserResponse> users = userService.getUsers(userSearchRequest);

        return ApiResponse.success(
                "Lấy danh sách người dùng thành công",
                users
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest userRequest
    ) {
        UserResponse userResponse = userService.updateUser(id, userRequest);

        return ApiResponse.success(
                "Cập nhật người dùng thành công",
                userResponse
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ApiResponse.success(
                "Xóa người dùng thành công",
                null
        );
    }

}
