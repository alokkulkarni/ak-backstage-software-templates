# OpenAPI Quick Start Guide

This application includes comprehensive API documentation using the OpenAPI (formerly known as Swagger) specification. This guide will help you access and use the built-in API documentation.

## Accessing the API Documentation

Once the application is running, you can access the API documentation in several ways:

### Option 1: Using the Swagger UI

The easiest way to explore the API is through the Swagger UI, which provides an interactive interface:

```
http://localhost:8585/swagger-ui.html
```

### Option 2: Raw OpenAPI Specification

You can access the raw OpenAPI specification in the following formats:

- JSON format: `http://localhost:8585/api-docs`
- YAML format: `http://localhost:8585/api-docs.yaml`

### Option 3: Using the Convenience Script

For your convenience, we've included a script that starts the application and automatically opens the Swagger UI:

```bash
# Make sure the script is executable first (do this once)
chmod +x openapi-view.sh

# Then run the script
./openapi-view.sh
```

## API Endpoints

The application exposes the following main endpoints:

### Health and Information Endpoints

- **Health Check**: `GET /api/health` - Basic health status
- **Enhanced Health Check**: `GET /api/v2/health` - Health status with timestamp
- **Application Info**: `GET /api/info` - Basic application information
- **Enhanced Application Info**: `GET /api/v2/info` - Structured application information

### Spring Actuator Endpoints

- **Actuator Health**: `GET /actuator/health` - Detailed system health
- **Actuator Info**: `GET /actuator/info` - Build and environment information
- **Actuator Metrics**: `GET /actuator/metrics` - Application metrics
- **Prometheus Metrics**: `GET /actuator/prometheus` - Prometheus-compatible metrics

## Customizing the OpenAPI Documentation

You can customize the OpenAPI configuration in the following files:

1. `src/main/java/com/example/${{values.java_package_name}}/config/OpenApiConfig.java`
2. `src/main/resources/application.yml` (under the `springdoc` section)
3. `src/main/resources/static/openapi.yaml` (static definition)

## Using OpenAPI for Client Generation

You can generate client libraries in various languages using the OpenAPI specification. For example:

```bash
# Install the OpenAPI Generator CLI
npm install -g @openapitools/openapi-generator-cli

# Generate a TypeScript client
openapi-generator-cli generate -i http://localhost:8585/api-docs -g typescript-axios -o ./generated/typescript-client

# Generate a Python client
openapi-generator-cli generate -i http://localhost:8585/api-docs -g python -o ./generated/python-client
```

## Further Resources

- [SpringDoc Documentation](https://springdoc.org/)
- [Swagger UI Documentation](https://swagger.io/tools/swagger-ui/)
- [OpenAPI Specification](https://spec.openapis.org/oas/latest.html)
