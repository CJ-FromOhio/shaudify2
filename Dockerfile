FROM gradle:jdk21-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon

FROM amazoncorretto:21-alpine-jdk
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/shaudify-1.0.0.jar
ENTRYPOINT ["java","-jar","/app/shaudify-1.0.0.jar"]
