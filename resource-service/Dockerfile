# =========================
# Build Stage
# =========================
FROM gradle:8.14.3-jdk17-alpine AS build
WORKDIR /app

COPY gradlew gradlew.bat settings.gradle build.gradle /app/
COPY gradle /app/gradle

RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon

COPY resource-service/build.gradle /app/resource-service/
COPY resource-service/src /app/resource-service/src

RUN ./gradlew :resource-service:assemble --no-daemon -x test

# =========================
# Runtime Stage
# =========================
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

COPY --from=build /app/resource-service/build/libs/*.jar app.jar

EXPOSE 8081

CMD ["java", "-jar", "app.jar"]