#!/bin/bash
set -e

# Build the application with SBOM generation
echo "Building Spring Boot application with SBOM using Gradle..."
./gradlew clean build cyclonedxBom

# Verify SBOM was generated
if [ -d "build/sbom" ]; then
  echo "✅ SBOM files generated successfully in build/sbom/"
  echo "SBOM files:"
  ls -la build/sbom/
else
  echo "❌ SBOM generation failed. Check Gradle configuration."
  exit 1
fi

# Optional: Run SBOM validation using cyclonedx CLI if installed
if command -v cyclonedx &> /dev/null; then
  echo "Validating SBOM files..."
  if [ -f "build/sbom/bom.json" ]; then
    cyclonedx validate --input-file build/sbom/bom.json
  fi
fi

# Build Docker image with embedded SBOM
echo "Building Docker image with embedded SBOM..."
docker build -t spring-demo-gradle/${{values.artifact_id}}:0.0.1-SNAPSHOT .

# Optional: Check for vulnerabilities in the SBOM if tools are available
if command -v grype &> /dev/null; then
  echo "Scanning dependencies for vulnerabilities..."
  grype dir:build/sbom/ -o json > vulnerability-report.json
  echo "Vulnerability report saved to vulnerability-report.json"
fi

# Apply Kubernetes configurations
echo "Applying Kubernetes configurations..."
kubectl apply -f k8s/deployment.yaml

# Wait for deployment to be ready
echo "Waiting for deployment to be ready..."
kubectl rollout status deployment/${{values.artifact_id}}

echo "Deployment complete! The application should be accessible via the ALB Ingress."
echo "Use 'kubectl get ingress ${{values.artifact_id}}' to get the ALB URL."
echo "SBOM files are embedded in the container at /app/sbom/"