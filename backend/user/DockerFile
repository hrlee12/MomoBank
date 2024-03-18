# 빌드 스테이지
FROM openjdk:17-jdk AS build
WORKDIR /app

COPY build.gradle .
COPY settings.gradle .
COPY gradle gradle
COPY gradlew .
COPY gradle.properties .
COPY src src

RUN chmod +x gradlew
RUN ./gradlew clean build

# 런타임 스테이지
FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
