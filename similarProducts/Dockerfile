# Use Eclipse Temurin 21 as base image (official OpenJDK)
FROM eclipse-temurin:21-jdk-jammy as builder

# Install Maven
RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy only the files needed for building to leverage Docker cache
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use a smaller runtime image
FROM eclipse-temurin:21-jre-jammy

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/similarproducts-1.0.0.jar app.jar

# Expose the port the app runs on
EXPOSE 5000

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
