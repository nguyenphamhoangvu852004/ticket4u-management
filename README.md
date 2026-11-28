# Ticket4U 

Hệ thống đặt vé theo kiến trúc DDD, sử dụng Spring Boot, MySQL, Redis, ...

## Cài đặt và chạy

<details>
<summary>Linux</summary>

#### Yêu cầu

- Bash shell
- Git
- JDK 21
- Maven
- Docker Engine và Docker Compose v2 (`docker compose`)

#### Khởi chạy

1. Clone nhánh `main` và vào thư mục dự án:

```bash
cd ~ && git clone -b main https://github.com/nguyenphamhoangvu852004/ticket4u-management.git && cd ticket4u-management
```

2. Cấp quyền và setup cơ sở hạ tầng 

```bash
chmod +x ./t4u && ./t4u
```

3. Chạy ứng dụng Spring Boot:

```bash
mvn spring-boot:run -pl ticket4u-start/
```

Ứng dụng lắng nghe tại `http://localhost:8081`.

Script `t4u` kiểm tra các lệnh Java, Maven và Docker có sẵn, sau đó dùng Docker Compose để khởi động MySQL, Redis và các dịch vụ monitoring/logging ở chế độ nền. Mặc định MySQL và Redis lần lượt được ánh xạ ra cổng `3307` và `6380` trên máy host.


### Quản lý cơ sở hạ tầng

```bash
./t4u --stop        # Dừng các container, giữ lại dữ liệu
./t4u --restart     # Khởi động lại các dịch vụ
./t4u --uninstall   # Xóa container và Docker volume
./t4u --help        # Xem trợ giúp
```

Lệnh `--uninstall` sẽ hỏi xác nhận trước khi xóa. Chạy từng tùy chọn riêng lẻ.

</details>

<details>
<summary>MacOS (Update later)</summary>
</details>

<details>
<summary>Window (Update later)</summary>


</details>

> 📌 Đây là bản thiết kế lại từ dự án Ticket4u microservices trước đó: [ticket4u-deprecated](https://github.com/nguyenphamhoangvu852004/ticket4u).
> Bản cũ gồm 3 service với 3 tech stack khác nhau; bản này tập trung vào thiết kế lấy domain làm trung tâm,
> đảm bảo tính nhất quán dữ liệu khi có nhiều request đồng thời, và hiệu năng.

# Architechture

<img width="395" height="539" alt="image" src="https://github.com/user-attachments/assets/ba74bbf2-da20-44b2-8eaa-0f6462922f4b" />

| Module | Trách nhiệm | Phụ thuộc |
|---|---|---|
| `ticket4u-controller` | Presentation Layer: tiếp nhận HTTP request, kiểm tra dữ liệu đầu vào, gọi use case và trả HTTP response. | `ticket4u-application` |
| `ticket4u-application` | Application Layer: triển khai các use case, điều phối nghiệp vụ, quản lý transaction và phối hợp truy cập dữ liệu. | `ticket4u-domain`, `ticket4u-infrastructure` |
| `ticket4u-domain` | Domain Layer: chứa entity, domain service và các quy tắc nghiệp vụ cốt lõi. | `ticket4u-infrastructure`, theo hướng phụ thuộc trong sơ đồ |
| `ticket4u-infrastructure` | Infrastructure Layer: cung cấp repository và các thành phần kỹ thuật phục vụ lưu trữ dữ liệu, cache hoặc tích hợp hệ thống bên ngoài. | Các thư viện và công nghệ tương ứng |
| `ticket4u-start` | Điểm khởi chạy ứng dụng Spring Boot, tập hợp các module và cấu hình dependency injection. | Các module cần thiết để chạy ứng dụng |
