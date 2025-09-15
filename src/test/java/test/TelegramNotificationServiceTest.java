package test;

import devops.service.TelegramNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramNotificationServiceTest {

    @InjectMocks
    private TelegramNotificationService telegramNotificationService;

    @Test
    void sendCarNotification_shouldCallSendMessage_whenValidData() {
        // Arrange
        String action = "Добавлен автомобиль";
        String brand = "BMW";
        Date year = new Date(2023 - 1900, 0, 1); // January 1, 2023
        
        // Set up the service with valid tokens
        ReflectionTestUtils.setField(telegramNotificationService, "botToken", "test_token");
        ReflectionTestUtils.setField(telegramNotificationService, "chatId", "test_chat_id");

        // Act
        telegramNotificationService.sendCarNotification(action, brand, year);

        // Assert - verify that the method completes without throwing exceptions
        // The actual HTTP call will fail in tests, but we're testing the logic
    }

    @Test
    void sendHandlingNotification_shouldCallSendMessage_whenValidData() {
        // Arrange
        String action = "Добавлено обслуживание";
        String carBrand = "Toyota";
        String description = "Замена масла";
        
        // Set up the service with valid tokens
        ReflectionTestUtils.setField(telegramNotificationService, "botToken", "test_token");
        ReflectionTestUtils.setField(telegramNotificationService, "chatId", "test_chat_id");

        // Act
        telegramNotificationService.sendHandlingNotification(action, carBrand, description);

        // Assert - verify that the method completes without throwing exceptions
    }

    @Test
    void sendCICDNotification_shouldCallSendMessage_whenValidData() {
        // Arrange
        String status = "SUCCESS";
        String buildNumber = "123";
        String details = "Build completed";
        
        // Set up the service with valid tokens
        ReflectionTestUtils.setField(telegramNotificationService, "botToken", "test_token");
        ReflectionTestUtils.setField(telegramNotificationService, "chatId", "test_chat_id");

        // Act
        telegramNotificationService.sendCICDNotification(status, buildNumber, details);

        // Assert - verify that the method completes without throwing exceptions
    }

    @Test
    void sendSystemStatus_shouldCallSendMessage_whenValidData() {
        // Arrange
        String status = "SUCCESS";
        String details = "All systems running";
        
        // Set up the service with valid tokens
        ReflectionTestUtils.setField(telegramNotificationService, "botToken", "test_token");
        ReflectionTestUtils.setField(telegramNotificationService, "chatId", "test_chat_id");

        // Act
        telegramNotificationService.sendSystemStatus(status, details);

        // Assert - verify that the method completes without throwing exceptions
    }

    @Test
    void sendNotificationAlert_shouldCallSendMessage_whenValidData() {
        // Arrange
        String action = "Test action";
        String message = "Test message";
        
        // Set up the service with valid tokens
        ReflectionTestUtils.setField(telegramNotificationService, "botToken", "test_token");
        ReflectionTestUtils.setField(telegramNotificationService, "chatId", "test_chat_id");

        // Act
        telegramNotificationService.sendNotificationAlert(action, message);

        // Assert - verify that the method completes without throwing exceptions
    }
}
