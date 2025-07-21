# ===== Build Stage =====
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy pom and source files
COPY . .

# Ensure mvnw is executable
RUN chmod +x mvnw

# Build the app
RUN ./mvnw clean package -DskipTests

# ===== Runtime Stage =====
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy built JAR from build stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 9090

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
