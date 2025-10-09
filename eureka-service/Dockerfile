# =========================
# Build Stage
# =========================
FROM gradle:8.14.3-jdk17-alpine AS build
WORKDIR /app

COPY gradlew gradlew.bat settings.gradle build.gradle /app/
COPY gradle /app/gradle

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY eureka-service/build.gradle /app/eureka-service/
COPY eureka-service/src /app/eureka-service/src

RUN ./gradlew :eureka-service:assemble --no-daemon -x test

# =========================
# Runtime Stage
# =========================
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

COPY --from=build /app/eureka-service/build/libs/*.jar app.jar

EXPOSE 8082

CMD ["java", "-jar", "app.jar"]