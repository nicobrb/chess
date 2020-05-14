FROM openjdk:8-alpine
RUN mkdir /app
COPY ./build/libs/scacchi-all.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "scacchi-all.jar"]