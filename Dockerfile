# FROM maven:3.8.5-openjdk-17
FROM maven:3.9.6-eclipse-temurin-21-alpine

WORKDIR /psb_tools
COPY . .
RUN mvn clean install

CMD mvn spring-boot:run