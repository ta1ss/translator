FROM node:latest AS frontend-builder
WORKDIR /app
COPY ./src/frontend/ ./
RUN npm install
RUN npm run build

FROM maven:latest AS backend-builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY --from=frontend-builder /app/dist ./src/main/resources/static
RUN mvn clean package -DskipTests

FROM openjdk:latest
WORKDIR /app
COPY --from=backend-builder /app/target/translator-0.0.1-SNAPSHOT.jar /app/translator.jar
EXPOSE 8080

CMD ["java", "-jar", "translator.jar"]

