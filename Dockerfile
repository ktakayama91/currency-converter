FROM adoptopenjdk/openjdk11:jdk-11.0.9.1_1-alpine-slim
EXPOSE 8080
COPY target/currency-converter-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
