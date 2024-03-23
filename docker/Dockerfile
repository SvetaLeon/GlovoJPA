#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -q -f /app/pom.xml clean package -DskipTests
# without -q you can take limit size problem

#
# Package stage
#
FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=/app/target/*.jar
COPY --from=build  ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]