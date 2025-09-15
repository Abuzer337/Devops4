package test;

import devops.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Mock
    private HttpSecurity httpSecurity;

    @Mock
    private org.springframework.security.web.DefaultSecurityFilterChain securityFilterChain;

    @Test
    void securityConfig_shouldBeInstantiated() {
        assertNotNull(securityConfig);
    }

    @Test
    void filterChain_shouldReturnSecurityFilterChain() throws Exception {
        // Mock the HttpSecurity behavior
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(securityFilterChain);

        // Act
        SecurityFilterChain result = securityConfig.filterChain(httpSecurity);

        // Assert
        assertNotNull(result);
        assertEquals(securityFilterChain, result);
        
        // Verify that the class has the expected annotations
        assertTrue(SecurityConfig.class.isAnnotationPresent(org.springframework.context.annotation.Configuration.class));
        assertTrue(SecurityConfig.class.isAnnotationPresent(org.springframework.security.config.annotation.web.configuration.EnableWebSecurity.class));
    }

    @Test
    void filterChain_shouldConfigureSecurityCorrectly() throws Exception {
        // Mock the HttpSecurity behavior
        when(httpSecurity.csrf(any())).thenReturn(httpSecurity);
        when(httpSecurity.authorizeHttpRequests(any())).thenReturn(httpSecurity);
        when(httpSecurity.build()).thenReturn(securityFilterChain);

        // Act
        securityConfig.filterChain(httpSecurity);

        // Assert - verify that all security configurations were called
        verify(httpSecurity).csrf(any());
        verify(httpSecurity).authorizeHttpRequests(any());
        verify(httpSecurity).build();
    }
}