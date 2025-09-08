FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY . .
ARG ENV
COPY deployment/enviroments/application-${ENV}.yaml /app/applications/app-service/src/main/resources/application.yaml
RUN chmod +x ./gradlew
RUN ./gradlew clean build -x validateStructure -x test --no-daemon

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/applications/app-service/build/libs/crediYaAutenticacion.jar .
CMD ["java", "-jar", "crediYaAutenticacion.jar"]