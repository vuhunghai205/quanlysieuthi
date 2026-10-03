package com.hunghaimart.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "nha_cung_cap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhaCungCap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_nha_cung_cap", nullable = false)
    private String tenNhaCungCap;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "dia_chi", columnDefinition = "TEXT")
    private String diaChi;

    @Column(name = "ngay_tao", updatable = false)
    private LocalDateTime ngayTao;

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
    }
}
