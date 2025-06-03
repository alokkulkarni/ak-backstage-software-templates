package com.example.${{values.java_package_name}}.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response model for the health endpoint.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Health status response")
public class HealthResponse {

    @Schema(description = "Application status", example = "UP", required = true)
    private String status;

    @Schema(description = "Timestamp when status was checked", example = "2025-06-03T12:30:45")
    private LocalDateTime timestamp;
}
