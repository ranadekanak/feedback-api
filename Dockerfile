### STAGE 1: Build ###

FROM maven:3.5.2-jdk-8 AS build

ARG PROJECT_ARTIFACTID=feedback-api

ARG PROJECT_VERSION=1.0-SNAPSHOT

COPY pom.xml ./pom.xml

COPY src ./src

RUN echo "running  mvn -f ./pom.xml install -Dproject.artifactId=${PROJECT_ARTIFACTID} -Dproject.version=${PROJECT_VERSION}"

RUN mvn -f ./pom.xml install -Dproject.artifactId=${PROJECT_ARTIFACTID} -Dproject.version=${PROJECT_VERSION}


### STAGE 2: Setup ###

FROM openjdk:8-jdk-alpine

VOLUME /tmp

ARG PROJECT_ARTIFACTID=feedback-api

ARG PROJECT_VERSION=1.0-SNAPSHOT

COPY --from=build target/${PROJECT_ARTIFACTID}-${PROJECT_VERSION}.jar app.jar

COPY --from=build src/main/resources src/main/resources

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
