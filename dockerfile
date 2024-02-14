# Use a base image with Java installed
FROM adoptopenjdk:11-jre-hotspot

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy the compiled artifacts into the container
COPY target/ /usr/src/app/

# Expose the port your Vert.x application listens on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "your-artifact-id-1.0.0.jar"]
