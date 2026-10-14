-- Thêm dữ liệu mẫu Sản phẩm độc lập cho Chi nhánh 2 (TP. Hồ Chí Minh)
INSERT INTO san_pham (ten_san_pham, ma_san_pham, danh_muc_id, chi_nhanh_id, nha_cung_cap_id, gia_ban, gia_von, so_luong_ton, muc_ton_toi_thieu, don_vi, hoat_dong) VALUES 
('Cà phê Phin Đen G7', 'SP014', 2, 2, 1, 45000, 35000, 800, 50, 'Hộp', TRUE),
('Bia Sài Gòn Special', 'SP015', 2, 2, 2, 17000, 13000, 1500, 100, 'Lon', TRUE),
('Bánh pía Sóc Trăng', 'SP016', 4, 2, 1, 65000, 48000, 400, 40, 'Hộp', TRUE),
('Nước mắm Phú Quốc 40N', 'SP017', 5, 2, 3, 85000, 65000, 300, 30, 'Chai', TRUE),
('Xoài Cát Hòa Lộc', 'SP018', 6, 2, 1, 70000, 50000, 120, 20, 'Kg', TRUE),
('Mì Omachi Xốt Bò Hầm', 'SP019', 1, 2, 1, 8500, 6500, 1200, 100, 'Gói', TRUE),
('Nước khoáng Lavie 500ml', 'SP020', 2, 2, 2, 6000, 4000, 900, 80, 'Chai', TRUE),
('Bột giặt Surf Nước Hoa', 'SP021', 3, 2, 3, 38000, 28000, 350, 30, 'Túi', TRUE),
('Tương cà Cholimex 270g', 'SP022', 5, 2, 3, 14000, 9500, 450, 40, 'Chai', TRUE),
('Khô bò Tây Nguyên cay', 'SP023', 4, 2, 1, 120000, 90000, 180, 20, 'Hộp', TRUE);

-- Giao dịch nhập kho khởi tạo cho Chi nhánh 2 (người tạo là Quản lý HCM - id 3)
INSERT INTO giao_dich_ton_kho (san_pham_id, chi_nhanh_id, so_luong, loai_giao_dich, ghi_chu, nguoi_tao_id)
SELECT id, 2, so_luong_ton, 'NHAP', 'Nhập kho ban đầu chi nhánh HCM', 3 
FROM san_pham 
WHERE ma_san_pham IN ('SP014', 'SP015', 'SP016', 'SP017', 'SP018', 'SP019', 'SP020', 'SP021', 'SP022', 'SP023');
