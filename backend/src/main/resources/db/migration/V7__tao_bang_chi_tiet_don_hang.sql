CREATE TABLE chi_tiet_don_hang (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    don_hang_id BIGINT NOT NULL,
    san_pham_id BIGINT NOT NULL,
    so_luong INT NOT NULL,
    gia_ban DECIMAL(15, 2) NOT NULL,
    tam_tinh DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (don_hang_id) REFERENCES don_hang(id),
    FOREIGN KEY (san_pham_id) REFERENCES san_pham(id)
);
