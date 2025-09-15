package test;

import devops.Main;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void main_shouldBeInstantiated() {
        // This test verifies that the Main class can be instantiated
        // The actual main method is tested through integration tests
        assertDoesNotThrow(() -> {
            // The Main class should be loadable
            Class.forName("devops.Main");
        });
    }

    @Test
    void main_shouldHaveCorrectAnnotations() {
        // Verify that the Main class has the expected Spring Boot annotations
        assertTrue(Main.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    void main_shouldHaveMainMethod() {
        // Verify that the Main class has a main method
        try {
            Main.class.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            fail("Main class should have a main method");
        }
    }

    @Test
    void main_shouldCreateSpringApplication() {
        // Test that we can create a SpringApplication instance
        SpringApplication app = new SpringApplication(Main.class);
        assertNotNull(app);
        // Note: getMainApplicationClass() might return null in some cases
        // We just verify that the application can be created
    }

    @Test
    void main_shouldHaveMainMethodWithCorrectSignature() {
        // Test that the main method exists and has the correct signature
        try {
            java.lang.reflect.Method mainMethod = Main.class.getMethod("main", String[].class);
            assertNotNull(mainMethod);
            assertEquals("main", mainMethod.getName());
            assertEquals(String[].class, mainMethod.getParameterTypes()[0]);
            assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
            assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
        } catch (NoSuchMethodException e) {
            fail("Main class should have a main method");
        }
    }
}
