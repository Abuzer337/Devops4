package devops.controller;

import devops.service.TelegramNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/telegram")
@CrossOrigin(origins = "*")
public class TelegramController {
    
    @Autowired
    private TelegramNotificationService telegramService;
    
    @PostMapping("/test")
    public String testTelegram(@RequestParam String message) {
        telegramService.sendMessage("🧪 Тестовое сообщение: " + message);
        return "Telegram notification sent!";
    }
    
    @PostMapping(value = "/system-status", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String sendSystemStatus(@RequestParam String status, @RequestParam String details) {
        try {
            String decodedDetails = new String(details.getBytes("ISO-8859-1"), "UTF-8");
            telegramService.sendSystemStatus(status, decodedDetails);
        } catch (Exception e) {
            telegramService.sendSystemStatus(status, details);
        }
        return "System status notification sent!";
    }
    
    @PostMapping(value = "/cicd", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    public String sendCICDNotification(@RequestParam String status, @RequestParam String buildNumber, @RequestParam String details) {
        // Принудительно устанавливаем кодировку UTF-8 для параметров
        try {
            String decodedDetails = new String(details.getBytes("ISO-8859-1"), "UTF-8");
            telegramService.sendCICDNotification(status, buildNumber, decodedDetails);
        } catch (Exception e) {
            // Если не удалось декодировать, используем оригинальный текст
            telegramService.sendCICDNotification(status, buildNumber, details);
        }
        return "CI/CD notification sent!";
    }
}
