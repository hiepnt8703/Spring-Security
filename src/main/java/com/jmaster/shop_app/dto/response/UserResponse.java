package com.jmaster.shop_app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String role;
}
