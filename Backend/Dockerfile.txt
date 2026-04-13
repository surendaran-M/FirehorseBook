# Step 1: Build the project
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
# Use . because you are already inside the Backend folder
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run the project
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
