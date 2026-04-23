FROM maven:3.9.9-eclipse-temurin-17

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

RUN cp target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]