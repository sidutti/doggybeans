FROM openjdk:11-jdk
ARG JAR_FILE=target/*.jar
ENV PORT 8080
EXPOSE 8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]