FROM eclipse-temurin:17-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/product-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app runs on (optional)
EXPOSE 8082

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]