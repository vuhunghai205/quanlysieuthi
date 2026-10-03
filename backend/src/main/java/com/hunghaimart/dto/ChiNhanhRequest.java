package com.hunghaimart.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChiNhanhRequest {
    @NotBlank(message = "Tên chi nhánh không được để trống")
    private String tenChiNhanh;

    @NotBlank(message = "Mã chi nhánh không được để trống")
    private String maChiNhanh;

    private String thanhPho;
    private String diaChi;
    private String soDienThoai;
    private String tenQuanLy;
    private Boolean hoatDong;
}
