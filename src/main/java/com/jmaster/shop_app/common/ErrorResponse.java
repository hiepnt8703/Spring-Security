package com.jmaster.shop_app.common;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    private Integer code;

    private String message;

    private String error;

    private LocalDateTime timestamp;
}
