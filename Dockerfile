FROM maven:latest as maven

WORKDIR /usr/src/app
COPY . .
RUN ./mvnw clean package -DskipTests

FROM adoptopenjdk/openjdk11:alpine-jre
ARG JAR_FILE=interview-planning-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} .

ENTRYPOINT ["java","-jar","interview-planning-0.0.1-SNAPSHOT.jar"]