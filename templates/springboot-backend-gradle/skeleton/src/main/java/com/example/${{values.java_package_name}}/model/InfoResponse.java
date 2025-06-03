package com.example.${{values.java_package_name}}.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Response model for the info endpoint.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Application information response")
public class InfoResponse {

    @Schema(description = "Application name", example = "Spring Demo Gradle", required = true)
    private String app;

    @Schema(description = "Application version", example = "0.0.1-SNAPSHOT", required = true)
    private String version;

    @Schema(description = "Build system used", example = "Gradle", required = true)
    private String buildType;

    @Schema(description = "Application features")
    private Map<String, String> features;
}
