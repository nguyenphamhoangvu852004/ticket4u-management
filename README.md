# Ticket4U 

Hệ thống đặt vé theo kiến trúc DDD, sử dụng Spring Boot, MySQL, Redis, ...

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
