# OpenAPI Documentation

This application includes OpenAPI (Swagger) documentation for the REST APIs.

## Accessing the API Documentation

Once the application is running, you can access the API documentation via:

### Swagger UI

The Swagger UI provides an interactive documentation interface:

```
http://localhost:8585/swagger-ui.html
```

### OpenAPI Specification

You can access the raw OpenAPI specification in the following formats:

- JSON format: `http://localhost:8585/api-docs`
- YAML format: `http://localhost:8585/api-docs.yaml`

## Available Endpoints

The application exposes the following main endpoints:

- **Health Check**: `GET /api/health`
- **Application Info**: `GET /api/info`
- **Actuator Endpoints**:
  - `GET /actuator/health`
  - `GET /actuator/info`
  - `GET /actuator/metrics`
  - `GET /actuator/prometheus`

## Configuration

The OpenAPI configuration is specified in:

1. `src/main/java/com/example/${{values.java_package_name}}/config/OpenApiConfig.java` - Java-based configuration
2. `src/main/resources/application.yml` - YAML-based configuration
3. `src/main/resources/static/openapi.yaml` - Static OpenAPI definition

## Implementation Details

This application uses:

- SpringDoc OpenAPI UI v2.4.0
- Spring Boot 3.x
- Swagger-UI for interactive documentation
