# Используем Gradle-образ для сборки
FROM gradle:8.4-jdk17 AS builder
WORKDIR /opt/app
COPY build.gradle settings.gradle ./
COPY ./src ./src
RUN gradle build -x test

# Используем меньший образ с Java для запуска
FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/build/libs/*.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
