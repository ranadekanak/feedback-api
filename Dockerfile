### STAGE 1: Build ###

FROM maven:3.5.2-jdk-8 AS build

COPY pom.xml ./pom.xml

COPY src ./src

RUN mvn -f ./pom.xml clean install -DskipTests 


### STAGE 2: Setup ###

FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY --from=build target/feedback-api.jar app.jar

COPY --from=build src/main/resources src/main/resources

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
