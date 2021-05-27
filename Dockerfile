FROM openjdk:8-jdk-alpine
ADD target/movie-manager-service.jar movie-manager-service.jar
EXPOSE 8080
#RUN addgroup -S spring && adduser -S spring -G spring
#USER spring:spring
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","movie-manager-service.jar"]