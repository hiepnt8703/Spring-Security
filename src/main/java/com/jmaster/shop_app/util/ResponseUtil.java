package com.jmaster.shop_app.util;

import common.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ResponseUtil {

    // ✅ SUCCESS (200)
    public static <T> ResponseEntity<BaseResponse<T>> success(T data) {
        return ResponseEntity.ok(
                BaseResponse.<T>builder()
                        .status(HttpStatus.OK.value())
                        .message("Success")
                        .data(data)
                        .build()
        );
    }

    // ✅ CREATED (201)
    public static <T> ResponseEntity<BaseResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(BaseResponse.<T>builder()
                        .status(HttpStatus.CREATED.value())
                        .message("Created successfully")
                        .data(data)
                        .build()
                );
    }

    // ❌ ERROR
    public static <T> ResponseEntity<BaseResponse<T>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(BaseResponse.<T>builder()
                        .status(status.value())
                        .message(message)
                        .data(null)
                        .build()
                );
    }

    // ❌ BAD REQUEST (400)
    public static <T> ResponseEntity<BaseResponse<T>> badRequest(String message) {
        return error(HttpStatus.BAD_REQUEST, message);
    }

    // ❌ UNAUTHORIZED (401)
    public static <T> ResponseEntity<BaseResponse<T>> unauthorized(String message) {
        return error(HttpStatus.UNAUTHORIZED, message);
    }

    // ❌ NOT FOUND (404)
    public static <T> ResponseEntity<BaseResponse<T>> notFound(String message) {
        return error(HttpStatus.NOT_FOUND, message);
    }
}
