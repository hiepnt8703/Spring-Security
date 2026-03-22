package com.jmaster.shop_app.controller;

import com.jmaster.shop_app.dto.request.LoginRequest;
import com.jmaster.shop_app.dto.request.RegisterRequest;
import com.jmaster.shop_app.dto.response.AuthResponse;
import com.jmaster.shop_app.dto.response.UserResponse;
import com.jmaster.shop_app.serivce.AuthService;
import com.jmaster.shop_app.util.ResponseUtil;
import common.BaseResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {

        String token = authService.login(request);

        return ResponseUtil.success(AuthResponse.builder()
                .token(token)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse userResponse = authService.register(request);
        return ResponseUtil.success(userResponse);
    }

}
