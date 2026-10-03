INSERT INTO chi_nhanh (ten_chi_nhanh, ma_chi_nhanh, thanh_pho, dia_chi, so_dien_thoai, ten_quan_ly, hoat_dong)
VALUES
('Hùng Hải Mart Hà Nội', 'HN', 'Hà Nội', '123 Cầu Giấy, Hà Nội', '0241234567', 'Nguyễn Văn Quản Lý', TRUE),
('Hùng Hải Mart Hồ Chí Minh', 'HCM', 'Hồ Chí Minh', '456 Lê Lợi, Q1, TP HCM', '0281234567', 'Trần Thị Quản Lý', TRUE);

-- Mật khẩu mặc định là 123456
INSERT INTO nguoi_dung (ho_ten, email, mat_khau, so_dien_thoai, vai_tro, chi_nhanh_id, kich_hoat)
VALUES
('Admin Toàn Hệ Thống', 'admin@hunghaimart.vn', '$2b$12$HgRy7ouUdgfyNTb6YGgT.OuTSj.PDKRMfxSVJNTm9lGjVSO9toln.', '0901000001', 'ADMIN', NULL, TRUE),
('Quản Lý HN', 'manager.hn@hunghaimart.vn', '$2b$12$HgRy7ouUdgfyNTb6YGgT.OuTSj.PDKRMfxSVJNTm9lGjVSO9toln.', '0901000002', 'MANAGER', 1, TRUE),
('Quản Lý HCM', 'manager.hcm@hunghaimart.vn', '$2b$12$HgRy7ouUdgfyNTb6YGgT.OuTSj.PDKRMfxSVJNTm9lGjVSO9toln.', '0901000003', 'MANAGER', 2, TRUE),
('Nhân Viên HN', 'staff.hn@hunghaimart.vn', '$2b$12$HgRy7ouUdgfyNTb6YGgT.OuTSj.PDKRMfxSVJNTm9lGjVSO9toln.', '0901000004', 'STAFF', 1, TRUE),
('Nhân Viên HCM', 'staff.hcm@hunghaimart.vn', '$2b$12$HgRy7ouUdgfyNTb6YGgT.OuTSj.PDKRMfxSVJNTm9lGjVSO9toln.', '0901000005', 'STAFF', 2, TRUE);
