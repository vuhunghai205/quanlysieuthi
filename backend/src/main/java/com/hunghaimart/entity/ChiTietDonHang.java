package com.hunghaimart.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "chi_tiet_don_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChiTietDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "don_hang_id", nullable = false)
    private DonHang donHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "san_pham_id", nullable = false)
    private SanPham sanPham;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong;

    @Column(name = "gia_ban", nullable = false)
    private BigDecimal giaBan;

    @Column(name = "tam_tinh", nullable = false)
    private BigDecimal tamTinh;
}
