# Báo Cáo Dự Án: Hệ Thống Quản Lý Siêu Thị (Hùng Hải Mart)

## 1. Tổng quan và Ý hiểu về Dự án
**Hùng Hải Mart** là một hệ thống phần mềm quản lý siêu thị toàn diện, được thiết kế để giải quyết bài toán vận hành thực tế của một chuỗi bán lẻ. Dự án được xây dựng theo kiến trúc **Monolithic (Backend)** kết hợp với giao diện **Single Page Application giả lập (Vanilla JS)**, tập trung vào hiệu năng, tính dễ sử dụng và tính bảo mật cao.

### Các Module chức năng cốt lõi:
1. **Quản lý Sản phẩm & Danh mục:** Quản lý thông tin chi tiết về hàng hóa, giá vốn, giá bán, hình ảnh và phân loại.
2. **Quản lý Nhà cung cấp:** Quản lý thông tin đối tác cung cấp hàng hóa, liên kết trực tiếp với luồng nhập kho.
3. **Quản lý Tồn kho:** Hệ thống ghi nhận các giao dịch nhập/xuất kho tự động. Cảnh báo sản phẩm sắp hết hạn mức tồn tối thiểu.
4. **Hệ thống Bán hàng (POS) & Đơn hàng:** Giao diện bán hàng nhanh gọn, tự động tính toán tổng tiền. Đặc biệt tích hợp hệ thống **Tích điểm Khách hàng** (mua 10.000đ tích 1 điểm, 1 điểm = 200đ) để giữ chân khách hàng (Loyalty).
5. **Báo cáo Tài chính & Hoạt động:** Thống kê chi tiết Doanh thu, Lợi nhuận gộp, Số lượng đơn hoàn tất và Tỷ lệ hủy đơn theo các mốc thời gian: Ngày, Tuần, Tháng, Năm.
6. **Bảo mật & Phân quyền:** Sử dụng JSON Web Token (JWT) để xác thực. Phân quyền chặt chẽ giữa Admin (Quản trị viên toàn quyền) và các Role nhân viên khác. Áp dụng chuẩn bảo mật mật khẩu cao (chữ hoa, số, ký tự đặc biệt, tối thiểu 8 ký tự).

### Công nghệ sử dụng:
- **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Spring Security (JWT), Flyway (Migration).
- **Frontend:** HTML5, CSS3 (Custom Variables), Vanilla JavaScript (Fetch API).
- **Cơ sở dữ liệu:** MySQL 8.
- **Triển khai:** Docker & Docker Compose.

---

## 2. Các công việc đã thực hiện
Xuyên suốt quá trình phát triển, các hạng mục công việc chính đã được hoàn thiện bao gồm:
- Thiết kế và chuẩn hóa lược đồ Cơ sở dữ liệu (Database Schema) đảm bảo các ràng buộc khóa chính/khóa ngoại hợp lý.
- Khởi tạo script Migration (Flyway) với lượng dữ liệu mẫu (Mock Data) lớn, mô phỏng hoạt động kinh doanh thực tế.
- Xây dựng toàn bộ các tầng Controller, Service, Repository cho các RESTful API.
- Lập trình giao diện Frontend trực quan, xử lý các luồng logic phức tạp (Giỏ hàng động, Tính điểm giảm giá, Lọc sản phẩm theo Nhà cung cấp).
- Giải quyết triệt để các vấn đề về Lazy Loading (Hibernate), lỗi CORS, và cấu hình tự động triển khai với Docker.
- Đóng gói toàn bộ ứng dụng thành 2 container (`frontend` và `backend`) kết nối với `mysql` container qua Docker network.

---

## 3. Hướng dẫn lấy mã nguồn và Khởi chạy (Dành cho người mới)

Dự án đã được đóng gói hoàn chỉnh bằng Docker, loại bỏ hoàn toàn rủi ro xung đột môi trường (không cần cài Java hay MySQL thủ công vào máy). Người khác chỉ cần thực hiện theo các bước sau:

### Yêu cầu hệ thống:
- Đã cài đặt **Git**.
- Đã cài đặt **Docker** và **Docker Compose**.

### Các bước khởi chạy:

**Bước 1: Tải mã nguồn về máy**
Mở Terminal (hoặc Git Bash/Command Prompt) và chạy lệnh:
```bash
git clone <đường-dẫn-repository-của-bạn>
cd quanlysieuthi
```

**Bước 2: Xây dựng và khởi chạy hệ thống**
Chạy lệnh Docker Compose để tự động tải MySQL, tự động build code Java và HTML:
```bash
# Đối với Linux / Ubuntu (có thể cần sudo):
sudo docker compose up -d --build

# Đối với Windows / macOS:
docker compose up -d --build
```

**Bước 3: Truy cập Ứng dụng**
Sau khi terminal báo `Started` cho tất cả các container, hãy mở trình duyệt web và truy cập vào:
👉 **http://localhost:3000**

**Bước 4: Đăng nhập hệ thống**
Sử dụng tài khoản Quản trị viên (Admin) mặc định đã được tạo sẵn trong Database:
- **Email:** `admin@hunghaimart.vn`
- **Mật khẩu:** `123456`

*(Lưu ý: Nếu muốn xóa toàn bộ dữ liệu cũ trong Database để làm lại từ đầu, hãy chạy lệnh `sudo docker compose down -v` trước khi chạy lệnh khởi động ở Bước 2).*
