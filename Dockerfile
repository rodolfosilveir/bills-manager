FROM gradle:7.5.0-jdk17 AS build
COPY . /app
WORKDIR /app
RUN chmod +x gradlew
RUN ./gradlew build

# Etapa de execução
FROM openjdk:17-jdk-slim
COPY --from=build /app/build/libs/bills-manager-0.0.1-SNAPSHOT.jar /app/bills-manager-0.0.1-SNAPSHOT.jar
RUN mkdir -p /app/csv/contas
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/bills-manager-0.0.1-SNAPSHOT.jar"]