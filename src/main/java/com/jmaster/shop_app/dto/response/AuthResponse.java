package com.jmaster.shop_app.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthResponse {
    String token;
}
