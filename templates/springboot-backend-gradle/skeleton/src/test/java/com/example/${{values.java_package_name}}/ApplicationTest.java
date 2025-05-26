package com.example.${{values.java_package_name}};

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
        assertNotNull(applicationContext, "Application context should not be null");
    }

    @Test
    void applicationStarts() {
        // This test ensures the main method can be called without errors
        // This approach tests the main() method coverage
        assertNotNull(applicationContext, "Application context should not be null");
        Application.main(new String[]{});
    }
}
