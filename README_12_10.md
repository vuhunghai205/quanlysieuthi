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

**Bước 2: Cấu hình biến môi trường (.env)**
Vì lý do bảo mật, file chứa mật khẩu Database không được đẩy lên Github. Mình đã để sẵn một file mẫu tên là `.env.example`. Bạn cần copy file này ra và đổi tên thành `.env`:
- **Đối với Linux / macOS (Terminal):**
  ```bash
  cp .env.example .env
  ```
- **Đối với Windows (Command Prompt / PowerShell):**
  ```cmd
  copy .env.example .env
  ```

**Bước 3: Xây dựng và khởi chạy hệ thống bằng Docker**
Chạy lệnh Docker Compose để tự động tải MySQL, tự động build code Java và HTML:
```bash
# Đối với Linux / Ubuntu (có thể cần sudo):
sudo docker compose up -d --build

# Đối với Windows / macOS:
docker compose up -d --build
```

**Bước 4: Truy cập Ứng dụng**
Sau khi terminal báo `Started` cho tất cả các container, hãy mở trình duyệt web và truy cập vào:
👉 **http://localhost:3000**

**Bước 5: Đăng nhập hệ thống**
Sử dụng tài khoản Quản trị viên (Admin) mặc định đã được tạo sẵn trong Database:
- **Email:** `admin@hunghaimart.vn`
- **Mật khẩu:** `123456`

*(Lưu ý: Nếu muốn xóa toàn bộ dữ liệu cũ trong Database để làm lại từ đầu, hãy chạy lệnh `sudo docker compose down -v` trước khi chạy lệnh khởi động ở Bước 2).*

---

## 4. Kiến trúc Hệ thống Chi tiết (System Architecture)
Dự án được xây dựng theo kiến trúc **N-Tier Architecture (Kiến trúc đa tầng)** cực kỳ chuẩn mực trong Java Spring Boot. Việc chia tầng giúp code dễ quản lý, dễ bảo trì và dễ mở rộng. Dưới đây là chức năng của từng tầng:

1. **Tầng Entity (Model):** 
   - Nơi định nghĩa các thực thể (bảng) trong Database thành các Class Java. Ví dụ: `NguoiDung`, `SanPham`, `DonHang`.
   - Sử dụng các Annotation của Hibernate (`@Entity`, `@Table`, `@ManyToOne`) để tự động ánh xạ (ORM) thành các bảng trong CSDL MySQL.

2. **Tầng DTO (Data Transfer Object):**
   - Không bao giờ trả thẳng Entity cho Client để tránh rò rỉ dữ liệu nhạy cảm (như mật khẩu) hoặc vòng lặp vô tận (Infinite Recursion).
   - DTO đóng vai trò là "chiếc giỏ" chuyên chở dữ liệu qua lại giữa Frontend và Backend. 
   - Tầng này còn chứa các Annotation kiểm tra tính hợp lệ dữ liệu (`@NotBlank`, `@Pattern`, `@Min`) giúp chặn dữ liệu xấu ngay từ cửa ngõ (Ví dụ: ép buộc độ khó của mật khẩu).

3. **Tầng Repository (Data Access Layer):**
   - Tầng giao tiếp trực tiếp với cơ sở dữ liệu.
   - Thừa kế `JpaRepository` của Spring Data JPA. Nó cung cấp sẵn các hàm như `save()`, `findAll()`, `findById()` mà không cần viết lệnh SQL thủ công.
   - Khi cần truy vấn phức tạp, lập trình viên có thể viết các câu lệnh `@Query` (JPQL).

4. **Tầng Service (Business Logic Layer):**
   - Đây là "trái tim" của hệ thống, nơi chứa toàn bộ logic nghiệp vụ (Tính tiền, Tính điểm thưởng, Kiểm tra tồn kho trước khi bán, Thuật toán mã hóa mật khẩu).
   - Tầng Controller chỉ gọi Service, không bao giờ được phép trực tiếp sửa dữ liệu bằng Repository.

5. **Tầng Controller (Presentation/API Layer):**
   - Đóng vai trò làm "Lễ tân", tiếp nhận các Request (GET, POST, PUT, DELETE) từ Frontend (qua đường dẫn `/api/...`).
   - Kiểm tra quyền hạn (vd: `@PreAuthorize("hasRole('ADMIN')")`), sau đó chuyển việc cho Service xử lý, rồi đóng gói kết quả (JSON) trả về cho người dùng (Frontend).

---

## 5. Giải thích các Công cụ & Framework cốt lõi

- **Spring Boot 3:** Framework mạnh mẽ nhất của Java hiện nay, giúp tạo ra các API chuẩn RESTful nhanh chóng với máy chủ Tomcat được nhúng sẵn.
- **Spring Security & JWT (JSON Web Token):** Đảm bảo an ninh tuyệt đối cho hệ thống. Khi người dùng đăng nhập thành công, máy chủ cấp cho một chuỗi Token. Mọi hành động tiếp theo đều phải xuất trình Token này để chứng minh nhân thân và phân quyền (Admin / Manager / Staff).
- **Spring Data JPA & Hibernate:** Công nghệ ORM (Object-Relational Mapping) giúp tương tác với cơ sở dữ liệu MySQL bằng hướng đối tượng mà không cần viết quá nhiều lệnh SQL dài dòng.
- **Flyway:** Công cụ quản lý phiên bản (Version Control) cho Database. Khi dự án khởi chạy, Flyway sẽ tự động chạy các kịch bản `.sql` (Migration) để tạo bảng và nạp dữ liệu mẫu tự động thay vì phải thao tác thủ công.

---

## 6. Sơ đồ Triển khai (Docker Deployment)
Hệ thống được đóng gói thành 3 máy ảo thu nhỏ (Container) chạy hoàn toàn độc lập nhưng giao tiếp với nhau qua một mạng ảo nội bộ (`hung-hai-network`):

1. `hung-hai-mart-db`: Chạy hệ quản trị CSDL **MySQL 8.0** ở cổng 3306. Dữ liệu được ánh xạ ra ngoài ổ cứng bằng Volume để không bị mất khi tắt máy.
2. `hung-hai-mart-backend`: Chạy lõi **Java Spring Boot** ở cổng 8080. Đóng vai trò là cầu nối cung cấp API và thao tác với DB.
3. `hung-hai-mart-frontend`: Chạy máy chủ **Nginx** ở cổng 3000, chứa các file giao diện HTML/CSS/JS. Nó đóng vai trò hiển thị giao diện và đẩy yêu cầu (Request) xuống cho Backend.
*(Ngoài ra còn tích hợp sẵn công cụ **Adminer** ở cổng 8081 để quản trị Database trực tiếp bằng giao diện web).*
