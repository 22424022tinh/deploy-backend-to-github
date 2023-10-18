# Sử dụng một hình ảnh cơ sở với OpenJDK 11
FROM adoptopenjdk:11-jdk-hotspot

# Thiết lập thư mục làm việc mặc định trong container
WORKDIR /app

# Sao chép tệp jar của ứng dụng vào thư mục làm việc
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Mở cổng mặc định của ứng dụng Spring Boot
EXPOSE 8080

# Chạy ứng dụng bằng lệnh java
CMD ["java", "-jar", "demo.jar"]
