package org.amazon.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Basic integration test that verifies the Spring Boot application context
 * can be loaded successfully. This test ensures that all components are
 * properly wired and the application can start without errors.
 */
@SpringBootTest
@ActiveProfiles("test")
class ExampleApplicationTests {

    /**
     * Tests that the Spring application context loads without any configuration
     * errors. This is a smoke test that catches major configuration issues
     * during the application startup process.
     */
    @Test
    void contextLoads() {
        // This test will fail if there are any Spring configuration errors
        // or missing dependencies. Success indicates the application can start properly.
    }
}
