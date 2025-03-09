package main.java.com.example.springdemogradle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }

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