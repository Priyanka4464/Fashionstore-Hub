# Step 1: Build the Java application using Maven
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Build the application, creating the .war file
RUN mvn clean package -DskipTests

# Step 2: Set up Tomcat to run the application
FROM tomcat:10.1-jdk21

# Remove default Tomcat webapps (optional, cleans up the ROOT context)
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# Copy the generated .war file from the build stage to the Tomcat webapps directory
# Renaming it to ROOT.war ensures it serves as the default app at the root URL (/)
COPY --from=build /app/target/FashionStore.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
