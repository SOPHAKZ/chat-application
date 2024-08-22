FROM amazoncorretto:17.0.7-alpine

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} chatapi.jar

ENTRYPOINT ["java" ,"-jar","/chatapi.jar"]

EXPOSE 8080
#
#FROM  maven:3.8.5-openjdk-17 AS build
#WORKDIR /app
#COPY pom.xml .
#COPY src ./src
#RUN mvn clean package -DskipTests
#
#FROM openjdk:17-jdk-slim
#WORKDIR /app
#COPY --from=build /app/target/student-0.0.1-SNAPSHOT.jar student-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java", "-jar", "/app/student-0.0.1-SNAPSHOT.jar"]