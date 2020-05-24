FROM openjdk:11-jre-slim
RUN mkdir /app
COPY build/libs/authorization-center.jar /app/
COPY src/main/resources/application-prod.yaml /app/
EXPOSE 8889

ENTRYPOINT ["java","-jar","/app/authorization-center.jar", "--spring.profiles.active=prod"]
