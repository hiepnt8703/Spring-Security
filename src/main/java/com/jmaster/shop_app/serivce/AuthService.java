package com.jmaster.shop_app.serivce;

import com.jmaster.shop_app.dto.request.LoginRequest;
import com.jmaster.shop_app.dto.request.RegisterRequest;
import com.jmaster.shop_app.dto.response.UserResponse;

public interface AuthService {
    String login(LoginRequest loginRequest);
    UserResponse register(RegisterRequest registerRequest);
}
