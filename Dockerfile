# api-gateway/Dockerfile
FROM openjdk:17-jdk-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]