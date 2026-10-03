package com.hunghaimart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TonKhoRequest {
    @NotNull(message = "ID Sản phẩm không được để trống")
    private Long sanPhamId;

    @NotNull(message = "ID Chi nhánh không được để trống")
    private Long chiNhanhId;

    @NotNull(message = "Số lượng không được để trống")
    private Integer soLuong;

    private String ghiChu;
}
