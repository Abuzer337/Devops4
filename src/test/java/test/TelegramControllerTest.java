package test;

import devops.controller.TelegramController;
import devops.service.TelegramNotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramControllerTest {

    @Mock
    private TelegramNotificationService telegramService;

    @InjectMocks
    private TelegramController telegramController;

    @Test
    void testTelegram_shouldReturnSuccessMessage() {
        String message = "Test message";

        String result = telegramController.testTelegram(message);

        assertEquals("Telegram notification sent!", result);
        verify(telegramService).sendMessage("🧪 Тестовое сообщение: " + message);
    }

    @Test
    void sendSystemStatus_shouldReturnSuccessMessage() {
        String status = "SUCCESS";
        String details = "All systems running";

        String result = telegramController.sendSystemStatus(status, details);

        assertEquals("System status notification sent!", result);
        verify(telegramService).sendSystemStatus(status, details);
    }

    @Test
    void sendCICDNotification_shouldReturnSuccessMessage() {
        String status = "SUCCESS";
        String buildNumber = "123";
        String details = "Build completed";

        String result = telegramController.sendCICDNotification(status, buildNumber, details);

        assertEquals("CI/CD notification sent!", result);
        verify(telegramService).sendCICDNotification(status, buildNumber, details);
    }
}
