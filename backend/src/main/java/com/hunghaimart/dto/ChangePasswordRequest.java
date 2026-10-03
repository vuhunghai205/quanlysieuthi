package com.hunghaimart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Mật khẩu hiện tại không được để trống")
    private String currentPassword;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    @Pattern(regexp = "^[A-Z](?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{7,}$", 
             message = "Mật khẩu phải dài ít nhất 8 ký tự, bắt đầu bằng chữ hoa, và chứa ít nhất một số và một ký tự đặc biệt")
    private String newPassword;

    @NotBlank(message = "Xác nhận mật khẩu không được để trống")
    private String confirmPassword;
}
