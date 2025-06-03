// filepath: /Users/alokkulkarni/Documents/Development/platformengineering/ak-backstage-software-templates/templates/springboot-backend-gradle/skeleton/src/main/java/com/example/${{values.java_package_name}}/controller/HealthController.java
package com.example.${{values.java_package_name}}.controller;

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

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Health", description = "Health and information endpoints")
public class HealthController {

    @Operation(
        summary = "Get application health status", 
        description = "Returns the current health status of the application"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Application is healthy", 
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = Map.class)
            )
        )
    })
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return ResponseEntity.ok(response);
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
                schema = @Schema(implementation = Map.class)
            )
        )
    })
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("app", "Spring Demo Gradle");
        response.put("version", "0.0.1-SNAPSHOT");
        response.put("buildType", "Gradle");
        
        Map<String, String> features = new HashMap<>();
        features.put("sbom", "enabled");
        features.put("actuator", "enabled");
        features.put("testing", "JUnit5 + JaCoCo + PIT");
        
        response.put("features", features);
        
        return ResponseEntity.ok(response);
    }
}
