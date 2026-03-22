package com.jmaster.shop_app.controller;

import com.jmaster.shop_app.dto.request.UserRequest;
import com.jmaster.shop_app.dto.response.UserResponse;
import com.jmaster.shop_app.serivce.UserService;
import com.jmaster.shop_app.util.ResponseUtil;
import common.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse userResponse = userService.createUser(request);
        return ResponseUtil.success(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {
        UserResponse userResponse = userService.updateUser(id, request);
        return ResponseUtil.success(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseUtil.success(userResponse);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Page<UserResponse>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase("DESC")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<UserResponse> users = userService.getAllUsers(pageable);
        return ResponseUtil.success(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseUtil.success(null);
    }
}
