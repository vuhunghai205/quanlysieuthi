package com.hunghaimart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KhachHangRequest {
    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String soDienThoai;

    private String diaChi;
}
