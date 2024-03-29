FROM openjdk:11-jdk
ARG JAR_FILE=Sgt-Sid/target/*.jar
ENV PORT 8080
ENV PORT 8558
ENV PORT 2552
ENV PORT 8081
EXPOSE 8080
EXPOSE 8558
EXPOSE 2552
EXPOSE 8081
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]