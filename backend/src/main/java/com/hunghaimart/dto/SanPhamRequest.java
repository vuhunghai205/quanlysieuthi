package com.hunghaimart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SanPhamRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String tenSanPham;

    @NotBlank(message = "Mã sản phẩm không được để trống")
    private String maSanPham;

    private String moTa;
    private Long danhMucId;
    private Long nhaCungCapId;
    private Long chiNhanhId;

    @NotNull(message = "Giá bán không được để trống")
    private BigDecimal giaBan;

    @NotNull(message = "Giá vốn không được để trống")
    private BigDecimal giaVon;

    private Integer mucTonToiThieu;
    private String donVi;
    private String anhUrl;
    private Boolean hoatDong;
}
