package test;

import devops.CorsFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import jakarta.servlet.ServletException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CorsFilterTest {

    @InjectMocks
    private CorsFilter corsFilter;

    @Test
    void doFilter_shouldAddCorsHeadersForGetRequest() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        // Act
        corsFilter.doFilter(request, response, filterChain);

        // Assert
        assertEquals("*", response.getHeader("Access-Control-Allow-Origin"));
        assertEquals("GET, POST, PUT, DELETE, OPTIONS, HEAD", response.getHeader("Access-Control-Allow-Methods"));
        assertEquals("authorization, content-type, xsrf-token", response.getHeader("Access-Control-Allow-Headers"));
        assertEquals("3600", response.getHeader("Access-Control-Max-Age"));
        assertEquals("xsrf-token", response.getHeader("Access-Control-Expose-Headers"));
    }

    @Test
    void doFilter_shouldHandleOptionsRequest() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        // Act
        corsFilter.doFilter(request, response, filterChain);

        // Assert
        assertEquals(200, response.getStatus());
        assertEquals("*", response.getHeader("Access-Control-Allow-Origin"));
        assertEquals("GET, POST, PUT, DELETE, OPTIONS, HEAD", response.getHeader("Access-Control-Allow-Methods"));
    }

    @Test
    void corsFilter_shouldBeInstantiated() {
        assertNotNull(corsFilter);
    }
}
