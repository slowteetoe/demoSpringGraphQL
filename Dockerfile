FROM gradle:jdk21 AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

RUN rm build/libs/*-plain.jar

FROM amazoncorretto:21-alpine-jdk

COPY --from=build /app/build/libs/*.jar /app/app.jar

# Set the working directory
WORKDIR /app

# Expose port 8080
EXPOSE 8080

# Start the application
CMD ["java", "-jar", "app.jar"]