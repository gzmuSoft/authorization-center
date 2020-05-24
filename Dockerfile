FROM gradle:5.2.1-jdk11 AS build
LABEL version="1.0.0-SNAPSHOT" description="授权中心" by="EchoCow"
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle shadowjar

FROM openjdk:11-jre-slim
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/ /app/
EXPOSE 8889

ENTRYPOINT ["java","-jar","/app/auth-center.jar", "-Dconfig=application-prod.yaml"]
