package com.hunghaimart.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "chi_nhanh")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiNhanh {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_chi_nhanh", nullable = false)
    private String tenChiNhanh;

    @Column(name = "ma_chi_nhanh", nullable = false, unique = true)
    private String maChiNhanh;

    @Column(name = "thanh_pho")
    private String thanhPho;

    @Column(name = "dia_chi")
    private String diaChi;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "ten_quan_ly")
    private String tenQuanLy;

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
        if (hoatDong == null) hoatDong = true;
    }

    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat = LocalDateTime.now();
    }
}
