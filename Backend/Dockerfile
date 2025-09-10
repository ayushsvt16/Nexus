# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline dependency:resolve-plugins

# Copy full source code
COPY src ./src

# Build the JAR
RUN mvn -B -q clean package -DskipTests

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy the fat jar built by Maven
COPY --from=builder /app/target/*.jar app.jar

# Expose local port (Render overrides with PORT env var)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["sh","-c","java -jar app.jar --server.port=${PORT:-8080}"]
