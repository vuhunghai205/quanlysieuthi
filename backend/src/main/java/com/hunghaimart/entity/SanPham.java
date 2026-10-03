package com.hunghaimart.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "san_pham")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_san_pham", nullable = false)
    private String tenSanPham;

    @Column(name = "ma_san_pham", nullable = false, unique = true)
    private String maSanPham;

    @Column(name = "mo_ta")
    private String moTa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "danh_muc_id")
    private DanhMuc danhMuc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chi_nhanh_id")
    private ChiNhanh chiNhanh;

    @Column(name = "gia_ban", nullable = false)
    private BigDecimal giaBan;

    @Column(name = "gia_von", nullable = false)
    private BigDecimal giaVon;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "muc_ton_toi_thieu")
    private Integer mucTonToiThieu;

    @Column(name = "don_vi")
    private String donVi;

    @Column(name = "anh_url")
    private String anhUrl;

    @Column(name = "hoat_dong")
    private Boolean hoatDong;

    @Column(name = "ngay_tao", updatable = false)
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
        ngayCapNhat = LocalDateTime.now();
        if (soLuongTon == null) soLuongTon = 0;
        if (mucTonToiThieu == null) mucTonToiThieu = 0;
        if (hoatDong == null) hoatDong = true;
    }

    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat = LocalDateTime.now();
    }
}
