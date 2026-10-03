# Hùng Hải Mart - Hệ thống quản lý siêu thị

## Mục tiêu dự án
Dự án xây dựng một phần mềm quản lý hoạt động siêu thị cho thương hiệu Hùng Hải Mart (chi nhánh Hà Nội và Hồ Chí Minh). Hệ thống hỗ trợ quản lý người dùng, chi nhánh, sản phẩm, đơn hàng, tồn kho và báo cáo doanh thu với các quyền hạn khác nhau (Admin, Manager, Staff).

## Kiến trúc hệ thống
- **Backend:** Java 21, Spring Boot 3.x, Spring MVC, Spring Data JPA, Hibernate, MySQL 8.0, Spring Security, JWT, Flyway.
- **Frontend:** HTML5, CSS3, JavaScript ES6+ (không dùng framework SPA), gọi qua Fetch API, deploy bằng Nginx.
- **Infrastructure:** Docker, Docker Compose, GitHub Actions cho CI/CD.

## Sơ đồ Docker
Toàn bộ hệ thống chạy qua Docker Compose, gồm 3 container chính:
- `hung-hai-mart-frontend` (Nginx, port 3000) -> Giao tiếp với người dùng và gọi API.
- `hung-hai-mart-backend` (Spring Boot, port 8080) -> Chạy logic nghiệp vụ, gọi db.
- `hung-hai-mart-db` (MySQL 8.0, port 3306) -> Lưu trữ dữ liệu với volume `mysql_data`.

## Yêu cầu cài đặt
- Docker và Docker Compose.

## Cách tạo môi trường (Environment)
Copy file `.env.example` thành `.env` để cấu hình thông tin database:
```bash
cp .env.example .env
```

## Cách chạy toàn bộ hệ thống
Dùng lệnh sau để build và chạy toàn bộ dịch vụ dưới dạng background:
```bash
docker compose up -d --build
```

## Các URL quan trọng
- **Frontend:** [http://localhost:3000](http://localhost:3000)
- **Backend API:** [http://localhost:8080/api](http://localhost:8080/api)
- **Backend Health:** [http://localhost:8080/api/health](http://localhost:8080/api/health)
- **MySQL Database:** `localhost:3306` (truy cập bằng công cụ như DBeaver với user và pass cấu hình trong `.env`)

## Phân biệt tài khoản
- **Tài khoản MySQL (Kỹ thuật):** Sử dụng các biến `MYSQL_USER`, `MYSQL_PASSWORD` từ file `.env` (chỉ dùng kết nối từ Spring Boot hoặc DBeaver, không dùng đăng nhập web).
- **Tài khoản Web:** Quản lý trong bảng `nguoi_dung`, sử dụng email và mật khẩu được mã hóa bằng BCrypt để đăng nhập tại giao diện web.

## Tài khoản Demo Website (Development Seed)
- **Admin toàn hệ thống:** `admin@hunghaimart.vn` / `123456`
- **Quản lý Hà Nội:** `manager.hn@hunghaimart.vn` / `123456`
- **Quản lý HCM:** `manager.hcm@hunghaimart.vn` / `123456`
- **Nhân viên Hà Nội:** `staff.hn@hunghaimart.vn` / `123456`
- **Nhân viên HCM:** `staff.hcm@hunghaimart.vn` / `123456`

## Cách đổi mật khẩu
Đăng nhập vào hệ thống web, sử dụng chức năng đổi mật khẩu trên giao diện. Mật khẩu mới sẽ được mã hóa và cập nhật vào database. Không làm thay đổi tài khoản kỹ thuật của MySQL.

## Danh sách API
(Đang cập nhật trong quá trình phát triển...)

## Cách chạy test
Để chạy các unit/integration test của backend:
```bash
./mvnw test
```
Hoặc nếu chạy trong container:
```bash
docker compose exec backend ./mvnw test
```

## Cách xem log
```bash
docker compose logs -f backend
docker compose logs -f frontend
docker compose logs -f db
```

## Cách Backup và Restore (Production)
Tạo thư mục backup và chạy lệnh `mysqldump` vào container MySQL (được lưu tại `backups/`):
```bash
mkdir -p backups
docker compose -f docker-compose.prod.yml exec -T db \
  mysqldump -u root -p"${MYSQL_ROOT_PASSWORD}" \
  --single-transaction --routines --triggers "${MYSQL_DATABASE}" \
  | gzip > "backups/hung_hai_mart_$(date +%Y%m%d_%H%M%S).sql.gz"
```

## CI/CD
Dự án sử dụng GitHub Actions:
- **CI:** Chạy unit test, build frontend/backend khi push hoặc pull request vào branch `main`.
- **CD:** Đẩy Docker image lên GHCR (GitHub Container Registry) và SSH vào server Ubuntu để cập nhật version mới (cần cấu hình Secret).

## Các secret cần cấu hình
Trên GitHub Repository, cấu hình các secret sau cho quá trình deploy (CD):
- `SERVER_HOST`
- `SERVER_USER`
- `SERVER_PORT`
- `SERVER_SSH_KEY`
- `GHCR_USERNAME` (nếu dùng private package)

## Cách rollback
Sử dụng tag commit SHA để sửa file `docker-compose.prod.yml` rồi chạy lại lệnh `docker compose up -d` nhằm rollback về phiên bản cụ thể.

> **Cảnh báo:** Tuyệt đối không dùng lệnh `docker compose down -v` trong môi trường production vì nó sẽ xóa toàn bộ Docker Volume chứa dữ liệu MySQL.