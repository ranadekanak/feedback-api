FROM openjdk:8-jdk-alpine

COPY target/*.jar app.jar

COPY src/main/resources src/main/resources

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
