package com.hunghaimart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NguoiDungRequest {
    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @jakarta.validation.constraints.Pattern(regexp = "^([A-Z](?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{7,})?$", message = "Mật khẩu phải dài ít nhất 8 ký tự, bắt đầu bằng chữ hoa, và chứa số, ký tự đặc biệt")
    private String matKhau;
    private String soDienThoai;
    
    @NotBlank(message = "Vai trò không được để trống")
    private String vaiTro;
    
    private Long chiNhanhId;
    private Boolean kichHoat;
}
