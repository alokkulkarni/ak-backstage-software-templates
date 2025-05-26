FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy application jar
COPY build/libs/*.jar app.jar

# Create SBOM directory and copy SBOM files
# RUN mkdir -p /app/sbom
# COPY build/sbom/*.json /app/sbom/
# COPY build/sbom/*.xml /app/sbom/

# Add SBOM label to provide metadata about the image
# LABEL org.opencontainers.image.sbom.path="/app/sbom"
# LABEL org.cyclonedx.sbom.format="JSON,XML"

EXPOSE 8585:8585
ENTRYPOINT ["java","-jar","app.jar"]