package com.jmaster.shop_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Username không được để trống")
    @Size(min = 3, max = 32, message = "Username phải từ 3 đến 32 ký tự")
    @Pattern(
            regexp = "^[a-z0-9._-]+$",
            message = "Username chỉ được chứa chữ thường, số, dấu chấm, gạch dưới hoặc gạch ngang"
    )
    private String username;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 8, message = "Password phải có tối thiểu 8 ký tự")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$",
            message = "Password phải có ít nhất 1 chữ cái và 1 chữ số"
    )
    private String password;

    @Size(max = 128, message = "Họ tên không được vượt quá 128 ký tự")
    private String fullName;

    @Email(message = "Email không đúng định dạng")
    @Size(max = 128, message = "Email không được vượt quá 128 ký tự")
    private String email;

    /**
     * 1: ACTIVE
     * 0: LOCKED
     */
    private String status;
}
