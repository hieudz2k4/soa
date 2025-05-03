FROM openjdk:19-alpine
LABEL authors="hieudz2004"

WORKDIR /app
COPY target/notification_service-0.0.1-SNAPSHOT.jar /app/notification_service.jar
ENTRYPOINT ["java", "-jar", "notification_service.jar"]
