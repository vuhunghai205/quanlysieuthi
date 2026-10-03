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

    private String matKhau;
    private String soDienThoai;
    
    @NotBlank(message = "Vai trò không được để trống")
    private String vaiTro;
    
    private Long chiNhanhId;
    private Boolean kichHoat;
}
