FROM maven:latest AS build
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTest

FROM openjdk:17-alpine
COPY --from=build /app/target/*.jar /app.jar
CMD ["java", "-jar", "/app.jar"]