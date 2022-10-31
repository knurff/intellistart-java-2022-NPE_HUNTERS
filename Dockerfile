FROM maven:3.8.6-openjdk-11 as build
WORKDIR /usr/app
COPY checkstyle.xml .
COPY pom.xml .
RUN mvn -q -ntp -B dependency:go-offline

COPY src/ ./src/
RUN mvn package

FROM adoptopenjdk/openjdk11:alpine-jre as runtime
COPY --from=build /usr/app/target/interview-planning-0.0.1-SNAPSHOT.jar /app/runner.jar
ENTRYPOINT java -jar /app/runner.jar