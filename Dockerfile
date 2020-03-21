FROM openjdk:8-alpine
COPY . /app
WORKDIR /app
RUN ./gradlew initialize
ENTRYPOINT ["./gradlew", "run"]