FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY target/api-*.jar /app.jar
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongo:27017/schoology", "-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]