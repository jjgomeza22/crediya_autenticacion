FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew

RUN ./gradlew clean build -x validateStructure -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /app/applications/app-service/build/libs/crediYaAutenticacion.jar .

EXPOSE 8080

CMD ["java", "-jar", "crediYaAutenticacion.jar"]