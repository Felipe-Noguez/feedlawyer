# Start with maven:3.8-eclipse-temurin-8-alpine
FROM maven:3.8-eclipse-temurin-8-alpine AS builder

LABEL maintainer="Felipe Noguez"
# Set the working directory to /app
WORKDIR /app
RUN mkdir /target && chmod 777 /target

# Copy the source code to the container
COPY pom.xml .
# COPY target/feedlawyer-0.0.1-SNAPSHOT.jar /target

# Build the application with Maven
RUN mvn package

FROM openjdk:17-ea-jdk
COPY --from=builder target/feedlawyer-0.0.1-SNAPSHOT.jar /feedlawyer-0.0.1-SNAPSHOT.jar

# Set environment variables if needed
ENV spring.datasource.url=jdbc:postgresql://babar.db.elephantsql.com/jnuclbaa
ENV spring.jpa.properties.hibernate.default_schema=jnuclbaa.public
ENV jwt.secret=noguez
ENV spring.datasource.username=jnuclbaa
ENV spring.datasource.password=6nHO4WL2iVFjL0CvfzWb-YgskqPY-egl

# Expose default Spring Boot port
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "/feedlawyer-0.0.1-SNAPSHOT.jar"]

#End
