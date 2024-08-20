FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} chatapi.jar

ENTRYPOINT ["java" ,"-jar","/chatapi.jar"]

EXPOSE 8080