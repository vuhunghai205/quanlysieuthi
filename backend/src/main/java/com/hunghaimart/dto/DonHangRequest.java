package com.hunghaimart.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DonHangRequest {
    // Thông tin khách hàng (Nếu khách vãng lai thì để trống)
    private String tenKhachHang;
    private String soDienThoai;
    
    // Số điểm muốn dùng (1 điểm = 1000 VND)
    private Integer diemSuDung;

    @NotNull(message = "Chi nhánh không được để trống")
    private Long chiNhanhId;

    @NotEmpty(message = "Đơn hàng phải có ít nhất 1 sản phẩm")
    @Valid
    private List<ChiTietDonHangRequest> chiTiet;
}
