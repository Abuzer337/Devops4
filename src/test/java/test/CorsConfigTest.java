package test;

import devops.CorsConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CorsConfigTest {

    @InjectMocks
    private CorsConfig corsConfig;

    @Mock
    private CorsRegistry corsRegistry;

    @Mock
    private org.springframework.web.servlet.config.annotation.CorsRegistration corsRegistration;

    @Test
    void corsConfigurationSource_shouldReturnCorsConfigurationSource() {
        // Act
        CorsConfigurationSource result = corsConfig.corsConfigurationSource();

        // Assert
        assertNotNull(result);
        assertTrue(result instanceof UrlBasedCorsConfigurationSource);
    }

    @Test
    void corsConfigurationSource_shouldHaveCorrectConfiguration() {
        // Act
        CorsConfigurationSource source = corsConfig.corsConfigurationSource();
        CorsConfiguration config = source.getCorsConfiguration(new org.springframework.mock.web.MockHttpServletRequest());

        // Assert
        assertNotNull(config);
        assertTrue(config.getAllowedOriginPatterns().contains("*"));
        assertTrue(config.getAllowedMethods().contains("GET"));
        assertTrue(config.getAllowedMethods().contains("POST"));
        assertTrue(config.getAllowedMethods().contains("PUT"));
        assertTrue(config.getAllowedMethods().contains("DELETE"));
        assertTrue(config.getAllowedMethods().contains("OPTIONS"));
        assertTrue(config.getAllowedMethods().contains("HEAD"));
        assertTrue(config.getAllowedHeaders().contains("*"));
        assertFalse(config.getAllowCredentials());
        assertEquals(3600L, config.getMaxAge());
    }

    @Test
    void addCorsMappings_shouldConfigureCorsMappings() {
        // Mock the CorsRegistry behavior
        when(corsRegistry.addMapping("/**")).thenReturn(corsRegistration);
        when(corsRegistration.allowedOriginPatterns("*")).thenReturn(corsRegistration);
        when(corsRegistration.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")).thenReturn(corsRegistration);
        when(corsRegistration.allowedHeaders("*")).thenReturn(corsRegistration);
        when(corsRegistration.allowCredentials(false)).thenReturn(corsRegistration);
        when(corsRegistration.maxAge(3600)).thenReturn(corsRegistration);

        // Act
        corsConfig.addCorsMappings(corsRegistry);

        // Assert
        verify(corsRegistry).addMapping("/**");
        verify(corsRegistration).allowedOriginPatterns("*");
        verify(corsRegistration).allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD");
        verify(corsRegistration).allowedHeaders("*");
        verify(corsRegistration).allowCredentials(false);
        verify(corsRegistration).maxAge(3600);
    }

    @Test
    void corsConfig_shouldBeInstantiated() {
        assertNotNull(corsConfig);
    }
}
