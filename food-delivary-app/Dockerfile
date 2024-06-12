# FROM eclipse-temurin:17-jdk-alpine
# VOLUME /tmp
# COPY target/*.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]


FROM openjdk:17-jdk
LABEL maintainer = "yavarkhan892300@gmail.com"
VOLUME [ "/tmp" ]
COPY target/user-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "java","-jar","/app.jar" ]
EXPOSE 8082
EXPOSE 3306
WORKDIR /app
