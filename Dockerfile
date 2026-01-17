# --- Giai đoạn 1: Builder ---
# Sử dụng Eclipse Temurin (bản chuẩn, ổn định nhất thay thế cho openjdk cũ)
FROM eclipse-temurin:25-jdk AS builder

WORKDIR /app

# Copy các file cấu hình Gradle trước để tận dụng Docker cache
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Cấp quyền thực thi
RUN chmod +x gradlew

# Copy source code sau cùng
COPY src src

# Build ứng dụng
RUN ./gradlew clean build -x test

# --- Giai đoạn 2: Runner ---
# Dùng bản JRE để chạy cho nhẹ (Java Runtime Environment)
FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy file JAR từ giai đoạn build
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose cổng
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]