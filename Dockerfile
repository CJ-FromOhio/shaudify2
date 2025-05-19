FROM openjdk:17 as build
EXPOSE 8080
WORKDIR /app
COPY ./build/libs/shaudifyMain-0.0.1-SNAPSHOT.jar /app/shaudify.jar
ENTRYPOINT ["java","-jar","shaudify.jar"]

#FROM gradle:8.4-jdk17-alpine AS build
#WORKDIR /app
#COPY build.gradle settings.gradle gradlew ./
#COPY gradle ./gradle
#RUN ./gradlew dependencies
#COPY src ./src
#RUN ./gradlew build --no-daemon
#
#FROM eclipse-temurin:17-jre-alpine as deploy
#WORKDIR /app
#COPY --from=builder /app/build/libs/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]
