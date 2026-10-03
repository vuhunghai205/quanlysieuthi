-- Dữ liệu mẫu danh mục
INSERT INTO danh_muc (ten_danh_muc, mo_ta) VALUES 
('Thực phẩm khô', 'Gạo, mì tôm, gia vị...'),
('Đồ uống', 'Nước ngọt, bia, rượu...'),
('Hóa mỹ phẩm', 'Bột giặt, nước lau sàn, dầu gội...');

-- Dữ liệu mẫu sản phẩm
INSERT INTO san_pham (ten_san_pham, ma_san_pham, danh_muc_id, chi_nhanh_id, gia_ban, gia_von, so_luong_ton, muc_ton_toi_thieu, don_vi, hoat_dong) VALUES 
('Mì Hảo Hảo Tôm Chua Cay', 'SP001', 1, 1, 4000, 3000, 1000, 50, 'Gói', TRUE),
('Nước ngọt Coca Cola 330ml', 'SP002', 2, 1, 10000, 7000, 500, 100, 'Lon', TRUE),
('Bột giặt OMO 800g', 'SP003', 3, 1, 45000, 35000, 200, 20, 'Túi', TRUE);

-- Giao dịch nhập kho ban đầu
INSERT INTO giao_dich_ton_kho (san_pham_id, chi_nhanh_id, so_luong, loai_giao_dich, ghi_chu, nguoi_tao_id) VALUES 
(1, 1, 1000, 'NHAP', 'Nhập kho ban đầu', 1),
(2, 1, 500, 'NHAP', 'Nhập kho ban đầu', 1),
(3, 1, 200, 'NHAP', 'Nhập kho ban đầu', 1);

-- Khách hàng mẫu
INSERT INTO khach_hang (ho_ten, so_dien_thoai, dia_chi) VALUES 
('Khách lẻ', '0000000000', 'Không có'),
('Nguyễn Văn A', '0987654321', 'Cầu Giấy, Hà Nội');

-- Đơn hàng mẫu
INSERT INTO don_hang (ma_don_hang, khach_hang_id, chi_nhanh_id, nguoi_tao_id, tong_tien, trang_thai, trang_thai_thanh_toan) VALUES 
('DH-MOCK1', 1, 1, 2, 40000, 'HOAN_THANH', 'DA_THANH_TOAN'),
('DH-MOCK2', 2, 1, 2, 45000, 'HOAN_THANH', 'DA_THANH_TOAN');

-- Chi tiết đơn hàng mẫu
INSERT INTO chi_tiet_don_hang (don_hang_id, san_pham_id, so_luong, gia_ban, tam_tinh) VALUES 
(1, 1, 10, 4000, 40000),
(2, 3, 1, 45000, 45000);
