package com.example.${{values.java_package_name}}.controller;

import com.example.${{values.java_package_name}}.model.HealthResponse;
import com.example.${{values.java_package_name}}.model.InfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Enhanced Health Controller using model classes and OpenAPI annotations
 * Note: This controller is an alternative to the basic HealthController
 * and can be used to replace it when ready.
 */
@RestController
@RequestMapping("/api/v2")
@Tag(name = "Health API (v2)", description = "Enhanced health and information endpoints with proper models")
public class HealthControllerEnhanced {

    @Operation(
        summary = "Get application health status", 
        description = "Returns the current health status of the application with timestamp"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Application is healthy", 
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = HealthResponse.class)
            )
        )
    })
    @GetMapping("/health")
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok(
            HealthResponse.builder()
                .status("UP")
                .timestamp(LocalDateTime.now())
                .build()
        );
    }

    @Operation(
        summary = "Get application information", 
        description = "Returns detailed information about the application, including version and features"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Application information retrieved successfully", 
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = InfoResponse.class)
            )
        )
    })
    @GetMapping("/info")
    public ResponseEntity<InfoResponse> info() {
        Map<String, String> features = new HashMap<>();
        features.put("sbom", "enabled");
        features.put("actuator", "enabled");
        features.put("testing", "JUnit5 + JaCoCo + PIT");
        features.put("openapi", "enabled");
        
        return ResponseEntity.ok(
            InfoResponse.builder()
                .app("Spring Demo Gradle")
                .version("0.0.1-SNAPSHOT")
                .buildType("Gradle")
                .features(features)
                .build()
        );
    }
}
