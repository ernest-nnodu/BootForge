FROM maven:3.9.4-eclipse-temurin-21-alpine
WORKDIR /app
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 \
    mvn dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 \
        mvn clean package -DskipTests

RUN cp target/*.jar app.jar
RUN addgroup -S app && adduser -S app -G app
RUN chown app:app app.jar
USER app
CMD ["java", "-jar", "app.jar"]
