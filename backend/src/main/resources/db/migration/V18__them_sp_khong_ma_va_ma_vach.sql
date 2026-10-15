-- Thêm cột mã vạch (barcode) cho sản phẩm
ALTER TABLE san_pham ADD COLUMN ma_vach VARCHAR(50);

-- Gán mã vạch EAN-13 thực tế cho các mặt hàng phổ biến
UPDATE san_pham SET ma_vach = '8934563138164' WHERE ma_san_pham = 'SP001'; -- Mì Hảo Hảo Tôm Chua Cay
UPDATE san_pham SET ma_vach = '8935049500544' WHERE ma_san_pham = 'SP002'; -- Coca Cola 330ml
UPDATE san_pham SET ma_vach = '8934868121111' WHERE ma_san_pham = 'SP003'; -- OMO 800g
UPDATE san_pham SET ma_vach = '8934680010111' WHERE ma_san_pham = 'SP004'; -- Bim bim Oishi
UPDATE san_pham SET ma_vach = '8936036010011' WHERE ma_san_pham = 'SP005'; -- Bánh Chocopie
UPDATE san_pham SET ma_vach = '8936017361234' WHERE ma_san_pham = 'SP006'; -- Nam Ngư
UPDATE san_pham SET ma_vach = '8936017362345' WHERE ma_san_pham = 'SP007'; -- Chinsu
UPDATE san_pham SET ma_vach = '8934822212345' WHERE ma_san_pham = 'SP011'; -- Bia Tiger
UPDATE san_pham SET ma_vach = '8934673123456' WHERE ma_san_pham = 'SP012'; -- Sữa Vinamilk

-- Bổ sung các mặt hàng không mã vạch / bán chạy (Nước Lavie to, đá, trứng...) cho Chi nhánh 1 (Hà Nội)
INSERT INTO san_pham (ten_san_pham, ma_san_pham, danh_muc_id, chi_nhanh_id, gia_ban, gia_von, so_luong_ton, muc_ton_toi_thieu, don_vi, anh_url, hoat_dong) VALUES 
('Nước khoáng Lavie bình 19L', 'LAVIE19L_HN', 2, 1, 65000, 50000, 100, 10, 'Bình', 'https://images.unsplash.com/photo-1548839140-29a749e1bc4e?auto=format&fit=crop&w=300&q=80', TRUE),
('Túi đá viên sạch 5kg', 'DAVIEN5KG_HN', 2, 1, 12000, 6000, 200, 20, 'Túi', 'https://images.unsplash.com/photo-1517420704952-d9f39e95b43e?auto=format&fit=crop&w=300&q=80', TRUE),
('Hộp trứng gà Ba Huân 10 quả', 'TRUNG10Q_HN', 1, 1, 32000, 25000, 150, 15, 'Hộp', 'https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?auto=format&fit=crop&w=300&q=80', TRUE);

-- Bổ sung cho Chi nhánh 2 (Hồ Chí Minh)
INSERT INTO san_pham (ten_san_pham, ma_san_pham, danh_muc_id, chi_nhanh_id, gia_ban, gia_von, so_luong_ton, muc_ton_toi_thieu, don_vi, anh_url, hoat_dong) VALUES 
('Nước khoáng Lavie bình 19L', 'LAVIE19L_HCM', 2, 2, 65000, 50000, 120, 10, 'Bình', 'https://images.unsplash.com/photo-1548839140-29a749e1bc4e?auto=format&fit=crop&w=300&q=80', TRUE),
('Túi đá viên sạch 5kg', 'DAVIEN5KG_HCM', 2, 2, 12000, 6000, 250, 20, 'Túi', 'https://images.unsplash.com/photo-1517420704952-d9f39e95b43e?auto=format&fit=crop&w=300&q=80', TRUE),
('Hộp trứng gà Ba Huân 10 quả', 'TRUNG10Q_HCM', 1, 2, 32000, 25000, 180, 15, 'Hộp', 'https://images.unsplash.com/photo-1582722872445-44dc5f7e3c8f?auto=format&fit=crop&w=300&q=80', TRUE);

