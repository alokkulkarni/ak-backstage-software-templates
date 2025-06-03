package com.example.${{values.java_package_name}}.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthControllerEnhanced.class)
class HealthControllerEnhancedTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // Verify the context loads
        assertNotNull(mockMvc, "MockMvc should be initialized");
    }

    @Test
    void health_shouldReturnUpStatusAndTimestamp() throws Exception {
        // Test the enhanced health endpoint
        mockMvc.perform(get("/api/v2/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("UP")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    void info_shouldReturnCompleteAppInfo() throws Exception {
        // Test the enhanced info endpoint
        mockMvc.perform(get("/api/v2/info")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.app", is("Spring Demo Gradle")))
                .andExpect(jsonPath("$.version", is("0.0.1-SNAPSHOT")))
                .andExpect(jsonPath("$.buildType", is("Gradle")))
                .andExpect(jsonPath("$.features").isMap())
                .andExpect(jsonPath("$.features.sbom", is("enabled")))
                .andExpect(jsonPath("$.features.actuator", is("enabled")))
                .andExpect(jsonPath("$.features.testing", is("JUnit5 + JaCoCo + PIT")))
                .andExpect(jsonPath("$.features.openapi", is("enabled")));
    }
}
