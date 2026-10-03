CREATE TABLE giao_dich_ton_kho (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    san_pham_id BIGINT NOT NULL,
    chi_nhanh_id BIGINT NOT NULL,
    so_luong INT NOT NULL,
    loai_giao_dich VARCHAR(50) NOT NULL,
    ghi_chu TEXT,
    nguoi_tao_id BIGINT NOT NULL,
    ngay_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (san_pham_id) REFERENCES san_pham(id),
    FOREIGN KEY (chi_nhanh_id) REFERENCES chi_nhanh(id),
    FOREIGN KEY (nguoi_tao_id) REFERENCES nguoi_dung(id)
);
