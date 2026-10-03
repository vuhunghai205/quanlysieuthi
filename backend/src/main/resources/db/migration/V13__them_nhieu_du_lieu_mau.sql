-- Thêm nhiều Danh mục
INSERT INTO danh_muc (ten_danh_muc, mo_ta) VALUES 
('Đồ ăn vặt', 'Bánh kẹo, bim bim, socola...'),
('Gia vị', 'Mắm, muối, mì chính, tương ớt...'),
('Đồ tươi sống', 'Thịt, cá, hải sản, rau củ...');

-- Thêm nhiều Sản phẩm
INSERT INTO san_pham (ten_san_pham, ma_san_pham, danh_muc_id, chi_nhanh_id, nha_cung_cap_id, gia_ban, gia_von, so_luong_ton, muc_ton_toi_thieu, don_vi, hoat_dong) VALUES 
('Bim bim Oishi', 'SP004', 4, 1, 1, 5000, 3000, 2000, 100, 'Gói', TRUE),
('Bánh Chocopie', 'SP005', 4, 1, 1, 45000, 35000, 500, 50, 'Hộp', TRUE),
('Nước mắm Nam Ngư', 'SP006', 5, 1, 3, 30000, 22000, 300, 30, 'Chai', TRUE),
('Tương ớt Chinsu', 'SP007', 5, 1, 3, 15000, 10000, 400, 40, 'Chai', TRUE),
('Thịt heo xay 500g', 'SP008', 6, 1, 1, 60000, 45000, 100, 10, 'Khay', TRUE),
('Cá hồi Nauy 200g', 'SP009', 6, 1, 1, 120000, 90000, 50, 5, 'Khay', TRUE),
('Rau muống sạch', 'SP010', 6, 1, 1, 10000, 5000, 150, 20, 'Bó', TRUE),
('Bia Tiger Bạc', 'SP011', 2, 1, 2, 18000, 14000, 1000, 100, 'Lon', TRUE),
('Sữa tươi Vinamilk', 'SP012', 2, 1, 1, 35000, 28000, 600, 50, 'Lốc', TRUE),
('Dầu gội Clear Men', 'SP013', 3, 1, 3, 150000, 110000, 200, 20, 'Chai', TRUE);

-- Thêm nhiều Khách hàng
INSERT INTO khach_hang (ho_ten, so_dien_thoai, diem_tich_luy) VALUES 
('Trần Thị B', '0912345678', 50),
('Lê Văn C', '0923456789', 120),
('Phạm Thị D', '0934567890', 0),
('Hoàng Văn E', '0945678901', 15),
('Ngô Thị F', '0956789012', 300);

-- Thêm nhiều Đơn hàng
INSERT INTO don_hang (ma_don_hang, khach_hang_id, chi_nhanh_id, nguoi_tao_id, tong_tien, giam_gia, trang_thai, trang_thai_thanh_toan, ngay_tao) VALUES 
('DH-MOCK3', 3, 1, 2, 150000, 0, 'HOAN_THANH', 'DA_THANH_TOAN', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY)),
('DH-MOCK4', 4, 1, 2, 45000, 0, 'HOAN_THANH', 'DA_THANH_TOAN', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 2 DAY)),
('DH-MOCK5', 5, 2, 3, 500000, 5000, 'HOAN_THANH', 'DA_THANH_TOAN', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 5 DAY)),
('DH-MOCK6', 6, 1, 2, 120000, 0, 'HOAN_THANH', 'DA_THANH_TOAN', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 WEEK)),
('DH-MOCK7', 7, 2, 3, 300000, 20000, 'HOAN_THANH', 'DA_THANH_TOAN', DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 MONTH));

-- Chi tiết đơn hàng mới
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES 
(3, 4, 10, 5000, 50000),
(3, 7, 10, 10000, 100000),
(4, 5, 1, 45000, 45000),
(5, 10, 2, 150000, 300000),
(5, 6, 1, 120000, 120000),
(5, 9, 2, 35000, 70000),
(6, 8, 5, 18000, 90000),
(6, 3, 1, 30000, 30000),
(7, 13, 2, 150000, 300000);
