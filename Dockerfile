FROM gradle:7.4.2-jdk17-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test

FROM openjdk:17-jdk-alpine
ARG JAR_PATH=/home/gradle/src/build/libs
COPY --from=build ${JAR_PATH}/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]