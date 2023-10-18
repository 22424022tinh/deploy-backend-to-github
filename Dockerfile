FROM maven:3.6.3-jdk-11 as build
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:11.0.11-jre-slim
COPY --from=build /target/*.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]
