package com.hunghaimart.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DonHangRequest {
    private Long khachHangId;

    @NotNull(message = "Chi nhánh không được để trống")
    private Long chiNhanhId;

    @NotEmpty(message = "Đơn hàng phải có ít nhất 1 sản phẩm")
    @Valid
    private List<ChiTietDonHangRequest> chiTiet;
}
