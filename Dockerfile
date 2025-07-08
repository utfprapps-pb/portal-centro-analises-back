FROM eclipse-temurin:17-jdk-alpine as build
WORKDIR /workspace/ca

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN sed -i 's/\r$//' mvnw

RUN /bin/sh mvnw package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /workspace/ca/target/lab-ca-1.jar lab-ca.jar
ENTRYPOINT ["java", "-jar", "lab-ca.jar"]