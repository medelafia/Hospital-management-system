# =========================
# Stage 1: Build
# =========================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only the POM first to leverage Docker layer caching —
# dependencies only re-download when pom.xml actually changes
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the actual source and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# =========================
# Stage 2: Runtime
# =========================
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

# Copy only the built jar from the build stage — nothing else
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Run as non-root for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ENTRYPOINT ["java", "-jar", "app.jar"]