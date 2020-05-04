FROM openjdk:8-alpine
RUN mkdir /app
COPY ./build/libs/scacchi-all-obf.jar /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "scacchi-all-obf.jar"]