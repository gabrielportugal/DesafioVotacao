# Etapa de build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Etapa de execução
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Configurações
ENV SPRING_PROFILES_ACTIVE=dev \
    SPRING_AUTOCONFIGURE_EXCLUDE=org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration \
    SPRING_CACHE_TYPE=none \
    SPRING_DATA_REDIS_ENABLED=false \
    SPRING_DEVTOOLS_RESTART_ENABLED=true

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]