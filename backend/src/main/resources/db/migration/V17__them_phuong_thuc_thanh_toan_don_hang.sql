-- Thêm cột phương thức thanh toán vào bảng don_hang (TIEN_MAT, CHUYEN_KHOAN)
ALTER TABLE don_hang ADD COLUMN phuong_thuc_thanh_toan VARCHAR(50) DEFAULT 'TIEN_MAT';

