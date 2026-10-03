CREATE TABLE don_hang (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ma_don_hang VARCHAR(50) NOT NULL UNIQUE,
    khach_hang_id BIGINT,
    chi_nhanh_id BIGINT NOT NULL,
    nguoi_tao_id BIGINT NOT NULL,
    tong_tien DECIMAL(15, 2) NOT NULL,
    trang_thai VARCHAR(50) NOT NULL,
    trang_thai_thanh_toan VARCHAR(50) NOT NULL,
    ngay_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ngay_cap_nhat TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (khach_hang_id) REFERENCES khach_hang(id),
    FOREIGN KEY (chi_nhanh_id) REFERENCES chi_nhanh(id),
    FOREIGN KEY (nguoi_tao_id) REFERENCES nguoi_dung(id)
);
