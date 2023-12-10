FROM openjdk:11-jre-slim

WORKDIR /app

COPY target .

RUN mv *.jar app.jar

EXPOSE 5701

ENTRYPOINT ["java", "-jar", "app.jar"]