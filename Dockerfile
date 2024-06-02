FROM maven:latest AS build
COPY . /app
WORKDIR /app
RUN mvn clean package

FROM openjdk:11-jre-slim
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]