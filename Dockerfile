# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom first to leverage Docker layer caching
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN mvn -q -DskipTests package dependency:copy-dependencies -DoutputDirectory=target/dependency

# ---------- Runtime stage ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# App classes and dependencies
COPY --from=build /app/target/classes /app/classes
COPY --from=build /app/target/dependency /app/dependency

# Default port used by the app (can be overridden by Render env PORT if you update the code to read it)
ENV PORT=8080
EXPOSE 8080

# Run the application main class
CMD [ "sh", "-c", "java -cp classes:dependency/* br.com.fiap.saude.Main" ]
