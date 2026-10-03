package com.hunghaimart.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "don_hang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma_don_hang", nullable = false, unique = true)
    private String maDonHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "khach_hang_id")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chi_nhanh_id", nullable = false)
    private ChiNhanh chiNhanh;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "nguoi_tao_id", nullable = false)
    private NguoiDung nguoiTao;

    @Column(name = "tong_tien", nullable = false)
    private BigDecimal tongTien;
    @Column(name = "giam_gia")
    private BigDecimal giamGia;

    @Column(name = "trang_thai", nullable = false)
    private String trangThai;

    @Column(name = "trang_thai_thanh_toan", nullable = false)
    private String trangThaiThanhToan;

    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ChiTietDonHang> chiTietDonHangs;

    @Column(name = "ngay_tao", updatable = false)
    private LocalDateTime ngayTao;

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
        ngayCapNhat = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat = LocalDateTime.now();
    }
}
