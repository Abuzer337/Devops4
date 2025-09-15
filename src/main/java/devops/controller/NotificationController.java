package devops.controller;

import devops.model.Notification;
import devops.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {
    private NotificationService service;

    @PostMapping("/create")
    public ResponseEntity<?> createNotification(@RequestBody Notification notification) {
        return ResponseEntity.ok(service.createNotification(notification));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateNotification(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.activate(id));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateNotification(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.deactivate(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNotification(@PathVariable("id") Long id, @RequestBody Notification notification) {
        return ResponseEntity.ok(service.updateNotification(notification, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotification(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getNotification(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getNotificationList() {
        return ResponseEntity.ok(service.getListNotifications());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable("id") Long id) {
        service.removeNotification(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{}");
    }
}
