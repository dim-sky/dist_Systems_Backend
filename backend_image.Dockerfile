FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-alpine-3.21
WORKDIR /app
RUN apk update && apk add curl

# Create group and non-root user and add user to the group
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /build/target/it21891_backend-0.0.1-SNAPSHOT.jar ./application.jar

RUN chown appuser /app/application.jar
USER appuser

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "application.jar"]

