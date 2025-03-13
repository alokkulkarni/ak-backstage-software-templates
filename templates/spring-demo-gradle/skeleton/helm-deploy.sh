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

# Optional: Run SBOM validation if cyclonedx CLI is installed
if command -v cyclonedx &> /dev/null; then
  echo "Validating SBOM files..."
  if [ -f "build/sbom/bom.json" ]; then
    cyclonedx validate --input-file build/sbom/bom.json
  fi
fi

# Build Docker image with embedded SBOM
echo "Building Docker image with embedded SBOM..."
docker build -t spring-demo-gradle/${{values.artifact_id}}:0.0.1-SNAPSHOT .

# Optional: Scan for vulnerabilities in the SBOM using grype if available
if command -v grype &> /dev/null; then
  echo "Scanning dependencies for vulnerabilities..."
  grype dir:build/sbom/ -o json > vulnerability-report.json
  echo "Vulnerability report saved to vulnerability-report.json"
fi

# Create directory for SBOM artifacts if using a repository
SBOM_ARTIFACTS_DIR="./sbom-artifacts"
mkdir -p $SBOM_ARTIFACTS_DIR
cp build/sbom/* $SBOM_ARTIFACTS_DIR/

# Helm deployment
echo "Deploying application using Helm..."
helm upgrade --install ${{values.artifact_id}} helm/${{values.artifact_id}} \
  --values helm/${{values.artifact_id}}/values.yaml \
  --values helm/${{values.artifact_id}}/values-dev.yaml \
  --namespace default \
  --create-namespace \
  --wait \
  --timeout 10m \
  --set sbom.enabled=true

# Wait for the application to be ready
echo "Waiting for application to be ready..."
kubectl rollout status deployment/${{values.artifact_id}}

echo "Deployment complete! Getting ingress URL..."
echo "ALB URL:"
kubectl get ingress ${{values.artifact_id}} -o jsonpath='{.status.loadBalancer.ingress[0].hostname}'
echo
echo "You can test the application using:"
echo "curl http://\$(kubectl get ingress ${{values.artifact_id}} -o jsonpath='{.status.loadBalancer.ingress[0].hostname}')/api/health"
echo
echo "SBOM files are embedded in the container at /app/sbom/ and stored locally in $SBOM_ARTIFACTS_DIR"