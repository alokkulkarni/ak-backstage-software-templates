# ${{values.artifact_id}} Application 

This is a sample Spring Boot application configured to use Gradle build system, with support for running on Amazon EKS with AWS ALB Ingress Controller.

## Prerequisites

- JDK 17
- Gradle 7.6+
- Docker
- kubectl configured with EKS cluster access
- AWS CLI configured with appropriate permissions

## Application Structure

- `src/main/java` - Java source code
  - `Application.java` - Main application class
  - `controller/HealthController.java` - REST endpoints
  - `config/OpenApiConfig.java` - OpenAPI configuration
- `src/main/resources`
  - `application.yml` - Application configuration
  - `static/openapi.yaml` - OpenAPI specification
- `k8s/` - Kubernetes deployment configurations
- `Dockerfile` - Container image definition
- `docs/` - Application documentation
  - `openapi.md` - API documentation
- `deploy.sh` - Deployment script
- `build.gradle` - Gradle build file with SBOM generation and testing

## Key Features

- Spring Boot 3.1.5 with Spring Web and Actuator
- Gradle build system with custom tasks for SBOM generation
- JaCoCo code coverage with 70% minimum coverage threshold
- PIT mutation testing with 70% threshold
- CycloneDX SBOM generation integrated into the build process
- Docker container with embedded SBOM for supply chain security
- Kubernetes and Helm deployment configurations
- Jenkins CI/CD pipeline integration
- OpenAPI documentation with Swagger UI
- Model-driven API design with proper schema definitions

## Available Endpoints

- `GET /api/health` - Health check endpoint
- `GET /api/info` - Application information endpoint
- `GET /api/v2/health` - Enhanced health check endpoint with timestamp
- `GET /api/v2/info` - Enhanced information endpoint with structured response
- `GET /swagger-ui.html` - Swagger UI for API documentation
- `GET /api-docs` - OpenAPI specification in JSON format
- `GET /api-docs.yaml` - OpenAPI specification in YAML format

## Build and Deploy

1. Make the deployment script executable:
   ```bash
   chmod +x deploy.sh
   ```

## API Documentation

This application includes comprehensive API documentation using OpenAPI (Swagger).

1. Run the application:
   ```bash
   ./gradlew bootRun
   ```

2. Access the Swagger UI:
   ```
   http://localhost:8585/swagger-ui.html
   ```

3. Or use the convenience script:
   ```bash
   ./openapi-view.sh
   ```

For more information, see the [API documentation guide](docs/openapi-quickstart.md).

2. Run the deployment script:
   ```bash
   ./deploy.sh
   ```
   
   This script will:
   - Build the Spring Boot application using Gradle
   - Generate SBOM (Software Bill of Materials)
   - Create a Docker image with embedded SBOM
   - Deploy to Kubernetes with the provided configurations

3. Check the deployment status:
   ```bash
   kubectl get pods -l app=${{values.artifact_id}}
   ```

4. Get the ALB URL:
   ```bash
   kubectl get ingress  
   ```

## Gradle Tasks

The project includes several custom Gradle tasks:

- `./gradlew build` - Build the application (includes SBOM generation)
- `./gradlew test` - Run unit tests
- `./gradlew jacocoTestReport` - Generate code coverage report
- `./gradlew jacocoTestCoverageVerification` - Verify code coverage meets thresholds
- `./gradlew pitest` - Run mutation tests
- `./gradlew cyclonedxBom` - Generate SBOM files in CycloneDX format
- `./gradlew qualityCheck` - Run all quality checks (tests, coverage, mutation, SBOM)
- `./gradlew copySbomArtifacts` - Copy generated SBOM files to a separate directory

## Testing the Application

1. Wait for the ALB to be provisioned (this may take a few minutes). You can check the status:
   ```bash
   kubectl describe ingress spring-demo-gradle
   ```

2. Once the ALB is ready, test the endpoints:
   ```bash
   # Health check
   curl http://<ALB-URL>/api/health

   # Application info
   curl http://<ALB-URL>/api/info
   ```

## Monitoring and Logs

### View application logs:
```bash
# Get pod name
POD_NAME=$(kubectl get pods -l app=${{values.artifact_id}} -o jsonpath='{.items[0].metadata.name}')

# Stream logs
kubectl logs -f $POD_NAME
```

### Check pod status:
```bash
kubectl describe pod -l app=${{values.artifact_id}}
```

### View metrics (via Spring Actuator):
```bash
curl http://<ALB-URL>/actuator/metrics
```

## SBOM (Software Bill of Materials)

The application includes SBOM generation capabilities to track all dependencies for security and compliance purposes.

### SBOM Generation

The SBOM is generated automatically during the Gradle build process using CycloneDX plugin. To manually generate the SBOM:

```bash
./gradlew cyclonedxBom
```

Generated SBOM files are located in:
```
build/sbom/bom.json
build/sbom/bom.xml
```

### SBOM Features

- **Format**: CycloneDX (both JSON and XML formats)
- **Schema Version**: 1.4
- **Scope**: Includes compile and runtime classpath dependencies
- **Content**: Comprehensive inventory of all direct and transitive dependencies
- **Embedded**: SBOMs are embedded in the Docker container at `/app/sbom/`

### SBOM-Based Vulnerability Scanning

The deployment scripts include optional vulnerability scanning of the SBOM using Grype:

```bash
# Manual scanning
grype dir:build/sbom/ -o json > vulnerability-report.json
grype dir:build/sbom/ -o table
```

## Helm Chart

The application includes a Helm chart for simplified deployment and management.

### Chart Structure
- `helm/${{values.artifact_id}}/`
  - `Chart.yaml` - Chart metadata and version information
  - `values.yaml` - Default configuration values
  - `values-dev.yaml` - Development environment specific values

### Installing the Helm Chart
1. Review and modify values in `values-dev.yaml` as needed
2. Install the chart:
   ```bash
   helm upgrade --install ${{values.artifact_id}} helm/${{values.artifact_id}} \
     --namespace dev \
     --create-namespace \
     -f helm/${{values.artifact_id}}/values-dev.yaml \
     --set sbom.enabled=true
   ```

3. Verify the installation:
   ```bash
   helm list -n dev
   kubectl get all -n dev -l app.kubernetes.io/name=${{values.artifact_id}}
   ```

### Helm Chart Configuration
Key configurations in `values.yaml`:
- Image repository and tag
- Replica count and autoscaling settings
- Resource requests and limits
- Ingress configurations
- Environment variables and configurations
- SBOM settings

## CI/CD Pipeline (Jenkins)
The application includes a Jenkins pipeline for automated building, testing, and deployment.

### Pipeline Stages
1. **Checkout**: Retrieves source code from version control
2. **Build**: Compiles the application using Gradle
3. **Test**: Runs unit tests and generates test reports
4. **Generate SBOM**: Creates Software Bill of Materials for the application
5. **Analyze SBOM for Vulnerabilities**: Scans dependencies for security issues
6. **SonarQube Analysis**: Performs code quality and security analysis
7. **Export Reports**: Uploads test, coverage, and SBOM reports to S3
8. **Build and Push Docker Image**: Creates and pushes container image with SBOM
9. **Container Security Scan**: Analyzes the container for vulnerabilities
10. **Deploy to EKS**: Deploys application using Helm

### Jenkins Prerequisites
- Jenkins with following tools configured:
  - JDK 17
  - Gradle
  - Docker
  - AWS CLI
  - Helm
  - kubectl
  - Optional: CycloneDX CLI, Grype, Cosign

### Running the Pipeline
Simply trigger the Jenkins pipeline using the provided Jenkinsfile. The pipeline will automatically:
- Build the application with Gradle
- Run tests and generate coverage reports
- Create and validate SBOM
- Scan for vulnerabilities
- Deploy to Kubernetes using Helm

## Differences from Maven Version

This Gradle-based project offers the same functionality as the Maven-based spring-demo, with the following differences:

1. Uses `build.gradle` instead of `pom.xml`
2. Employs the Gradle CycloneDX plugin instead of the Maven plugin
3. Generated files are in `build/` directory instead of `target/`
4. Custom Gradle tasks instead of Maven goals
5. Simplified plugin configuration
6. Uses Gradle's test task instead of Surefire
7. SBOM files are generated in `build/sbom/` instead of `target/sbom/`

## Troubleshooting

1. If pods are not starting:
   ```bash
   kubectl describe pod -l app=${{values.artifact_id}}
   ```

2. If ingress is not working:
   ```bash
   kubectl describe ingress ${{values.artifact_id}}
   ```

3. Gradle build issues:
   ```bash
   # Run with --debug for detailed information
   ./gradlew build --debug
   ```

4. SBOM-related issues:
   ```bash
   # Check if SBOM files were generated
   ls -la build/sbom/
   
   # Verify SBOM is in container
   kubectl exec $(kubectl get pod -l app=${{values.artifact_id}} -o jsonpath="{.items[0].metadata.name}") -- ls -la /app/sbom/
   ```

## Security Considerations

- The application is exposed via ALB Ingress
- Container runs as non-root user
- Resource limits are set to prevent resource exhaustion
- Health checks are configured for proper pod lifecycle management
- SBOM generation enables dependency tracking and vulnerability management
- Container images include SBOMs for supply chain security

## Additional Notes

- The application uses Spring Boot Actuator for health monitoring
- Kubernetes probes are configured for reliable health checking
- The ALB is configured in internet-facing mode with IP target type
- The service uses ClusterIP type as it's accessed through the ALB
- SBOM files are automatically generated and embedded in container images