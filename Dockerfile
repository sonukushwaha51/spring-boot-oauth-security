FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml file separately to leverage build cache.
# This layer is only invalidated when pom.xml changes.
COPY pom.xml .

# Download project dependencies (cache layer)
RUN mvn dependency:go-offline -B

# Copy the source code (this layer is invalidated on code changes)
COPY src ./src

# Compile and package the application
RUN mvn clean package -DskipTests

# Stage 2: Deploy the WAR to a lightweight JRE/Tomcat image
FROM tomcat:10.0-jdk17-temurin-focal
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/spring-boot-oath-security.war
EXPOSE 8080
CMD ["catalina.sh", "run"]