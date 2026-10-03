CREATE TABLE nha_cung_cap (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ten_nha_cung_cap VARCHAR(255) NOT NULL,
    so_dien_thoai VARCHAR(20),
    dia_chi TEXT,
    ngay_tao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE san_pham ADD COLUMN nha_cung_cap_id BIGINT;
ALTER TABLE san_pham ADD CONSTRAINT fk_sp_ncc FOREIGN KEY (nha_cung_cap_id) REFERENCES nha_cung_cap(id);

-- Dữ liệu mẫu
INSERT INTO nha_cung_cap (ten_nha_cung_cap, so_dien_thoai, dia_chi) VALUES 
('Công ty Acecook', '02838154000', 'KCN Tân Bình, TP HCM'),
('Công ty Coca Cola Việt Nam', '02838961000', 'Thủ Đức, TP HCM'),
('Unilever Việt Nam', '02838236651', 'Quận 7, TP HCM');

-- Cập nhật sản phẩm mẫu
UPDATE san_pham SET nha_cung_cap_id = 1 WHERE ma_san_pham = 'SP001';
UPDATE san_pham SET nha_cung_cap_id = 2 WHERE ma_san_pham = 'SP002';
UPDATE san_pham SET nha_cung_cap_id = 3 WHERE ma_san_pham = 'SP003';
