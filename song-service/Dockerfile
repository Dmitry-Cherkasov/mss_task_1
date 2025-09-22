# =========================
# Build Stage
# =========================
FROM gradle:6.9.1-jdk17-alpine AS build
WORKDIR /app

COPY gradlew gradlew.bat settings.gradle build.gradle /app/
COPY gradle /app/gradle

COPY song-service/build.gradle /app/song-service/
COPY song-service/src /app/song-service/src

RUN ./gradlew :song-service:assemble --no-daemon -x test

# =========================
# Runtime Stage
# =========================
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

COPY --from=build /app/song-service/build/libs/*.jar app.jar

EXPOSE 8082

CMD ["java", "-jar", "app.jar"]