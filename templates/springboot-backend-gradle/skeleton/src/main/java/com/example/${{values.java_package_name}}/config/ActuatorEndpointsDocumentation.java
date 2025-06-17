package com.example.${{values.java_package_name}}.config;

import io.swagger.v3.oas.annotations.Operation;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Configuration class to document Spring Boot Actuator endpoints in OpenAPI.
 * This is a workaround since Actuator endpoints are not automatically documented by SpringDoc.
 */
@Configuration
public class ActuatorEndpointsDocumentation {

    @RouterOperations({
        @RouterOperation(
            path = "/actuator/health",
            method = RequestMethod.GET,
            beanClass = Object.class,
            beanMethod = "handle",
            operation = @Operation(
                operationId = "getActuatorHealth",
                summary = "Health Endpoint",
                description = "Provides detailed health information about the application",
                tags = { "Actuator" }
            )
        ),
        @RouterOperation(
            path = "/actuator/info",
            method = RequestMethod.GET,
            beanClass = Object.class,
            beanMethod = "handle",
            operation = @Operation(
                operationId = "getActuatorInfo",
                summary = "Info Endpoint",
                description = "Provides information about the application",
                tags = { "Actuator" }
            )
        ),
        @RouterOperation(
            path = "/actuator/metrics",
            method = RequestMethod.GET,
            beanClass = Object.class,
            beanMethod = "handle",
            operation = @Operation(
                operationId = "getActuatorMetrics",
                summary = "Metrics Endpoint",
                description = "Provides metrics information about the application",
                tags = { "Actuator" }
            )
        ),
        @RouterOperation(
            path = "/actuator/prometheus",
            method = RequestMethod.GET,
            beanClass = Object.class,
            beanMethod = "handle",
            operation = @Operation(
                operationId = "getActuatorPrometheus",
                summary = "Prometheus Endpoint",
                description = "Exposes metrics in a format that can be scraped by a Prometheus server",
                tags = { "Actuator" }
            )
        )
    })
    public void actuatorEndpointsDocumentation() {
        // This method is intentionally left empty.
        // It serves to register the actuator endpoints documentation with SpringDoc.
        
    }
}

