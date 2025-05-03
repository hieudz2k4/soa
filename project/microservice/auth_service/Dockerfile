FROM openjdk:19-alpine
LABEL authors="hieudz"
WORKDIR /app
COPY target/auth_service-0.0.1-SNAPSHOT.jar /app/auth_service.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "auth_service.jar"]
