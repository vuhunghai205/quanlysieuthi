CREATE TABLE san_pham (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ten_san_pham VARCHAR(255) NOT NULL,
    ma_san_pham VARCHAR(50) NOT NULL UNIQUE,
    mo_ta TEXT,
    danh_muc_id BIGINT,
    chi_nhanh_id BIGINT,
    gia_ban DECIMAL(15, 2) NOT NULL,
    gia_von DECIMAL(15, 2) NOT NULL,
    so_luong_ton INT DEFAULT 0,
    muc_ton_toi_thieu INT DEFAULT 0,
    don_vi VARCHAR(50),
    anh_url TEXT,
    hoat_dong BOOLEAN DEFAULT TRUE,
    ngay_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ngay_cap_nhat TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (danh_muc_id) REFERENCES danh_muc(id),
    FOREIGN KEY (chi_nhanh_id) REFERENCES chi_nhanh(id)
);
