-- Fix lại mã hóa mật khẩu cho tất cả tài khoản người dùng về '123456'
-- Sử dụng chuẩn BCrypt 60 ký tự ($2a$10$...) để Spring Security giải mã thành công
UPDATE nguoi_dung 
SET mat_khau = '$2a$10$7a7sRyoYLE7p9N6OskxmPO50EiKKZTB7UOX9j2/ZemrO50tAW9wGq'
WHERE email IN (
    'admin@hunghaimart.vn',
    'manager.hn@hunghaimart.vn',
    'manager.hcm@hunghaimart.vn',
    'staff.hn@hunghaimart.vn',
    'staff.hcm@hunghaimart.vn'
);

