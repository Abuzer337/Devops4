FROM eclipse-temurin:21-jdk-alpine

# Копируем fat JAR
COPY build/libs/DevOps-1.0-SNAPSHOT.jar /app.jar

# Запуск через Spring Boot loader
ENTRYPOINT ["java", "-jar", "/app.jar"]