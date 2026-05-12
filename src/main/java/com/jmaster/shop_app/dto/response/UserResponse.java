package com.jmaster.shop_app.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;

    private String username;

    private String fullName;

    private String email;

    private String status;

    private String statusName;

    private LocalDateTime createdAt;
}
